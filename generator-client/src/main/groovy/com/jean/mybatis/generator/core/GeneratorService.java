package com.jean.mybatis.generator.core;

import com.jean.mybatis.generator.utils.StringUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class GeneratorService extends Service<List<String>> {

    private Configuration configuration;
    private ShellCallback callback;


    @Override
    protected Task<List<String>> createTask() {
        return new GeneratorTask(configuration, callback);

    }

    @Override
    public synchronized void restart() {
        //not support
    }

    public void restart(Configuration configuration, ShellCallback callback) {
        if (!isRunning()) {
            this.configuration = configuration;
            this.callback = callback;
            super.restart();
        }
    }


    private static class GeneratorTask extends Task<List<String>> implements ProgressCallback {

        private final Configuration configuration;
        private final ShellCallback shellCallback;
        private int saveTaskIndex = 1;
        private int introspectionTaskIndex = 1;
        private int generationTaskIndex = 1;

        GeneratorTask(Configuration configuration, ShellCallback shellCallback) {
            this.configuration = configuration;
            this.shellCallback = shellCallback;
        }

        @Override
        protected List<String> call() throws Exception {
            List<String> warnings = new ArrayList<>();
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
            myBatisGenerator.generate(this);
            return warnings;
        }

        @Override
        public void introspectionStarted(int totalTasks) {
            updateMessage("正在检查文件" + introspectionTaskIndex + "/" + totalTasks);
            introspectionTaskIndex++;
        }

        @Override
        public void generationStarted(int totalTasks) {
            updateMessage("正在生成文件" + generationTaskIndex + "/" + totalTasks);
            generationTaskIndex++;
        }

        @Override
        public void saveStarted(int totalTasks) {
            updateMessage("正在保存文件" + saveTaskIndex + "/" + totalTasks);
            saveTaskIndex++;
        }

        @Override
        public void startTask(String taskName) {
            updateMessage("正在执行" + taskName + "...");
        }

        @Override
        public void checkCancel() {
            updateMessage("取消生成");
        }

        @Override
        protected void succeeded() {
            List<String> warnings = getValue();
            if (warnings.isEmpty()) {
                updateMessage("生成成功");
            } else {
                String msg = StringUtil.join(warnings, ";\r\n");
                updateMessage("警告：" + msg);
                System.err.println(msg);
            }
        }

        @Override
        public void done() {
        }

        @Override
        protected void failed() {
            Throwable throwable = getException();
            if (throwable != null) {
                updateMessage("生成失败, " + throwable.getMessage());
            } else {
                updateMessage("生成失败");
            }
        }
    }

}
