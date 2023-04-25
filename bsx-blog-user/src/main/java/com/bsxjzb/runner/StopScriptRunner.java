package com.bsxjzb.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Component
@ConditionalOnProperty(name = "env.active", havingValue = "pro")
@Slf4j
public class StopScriptRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        String script = "";
        String scriptName = "";
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String pid = bean.getName().split("@")[0];
        if (os.contains("win")) {
            script = MessageFormat.format("taskkill /pid {0} /F /T", pid);
            scriptName = "stop.bat";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix") || os.contains("mac")) {
            script = MessageFormat.format("kill -15 {0}", pid);
            scriptName = "stop.sh";
        } else {
            log.warn("当前系统不支持生成停止脚本: {}", os);
        }
        File file = new File(scriptName);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(script.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("生成停止脚本异常");
        } finally {
            fos.flush();
            fos.close();
        }
        log.info("生成停止脚本完成:{}", scriptName);
    }

    public static void deleteStopScript() {
        File file = new File("stop.bat");
        File file1 = new File("stop.sh");
        if (file.exists()) {
            file.delete();
        }
        if (file1.exists()) {
            file1.delete();
        }
    }
}
