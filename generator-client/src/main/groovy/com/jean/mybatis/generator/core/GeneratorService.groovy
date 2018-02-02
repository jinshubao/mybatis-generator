package com.jean.mybatis.generator.core

import javafx.concurrent.Service
import javafx.concurrent.Task
import org.mybatis.generator.api.MyBatisGenerator
import org.mybatis.generator.api.ProgressCallback
import org.mybatis.generator.api.ShellCallback
import org.mybatis.generator.config.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@org.springframework.stereotype.Service
class GeneratorService extends Service<List<String>> {

    private Configuration configuration
    private ShellCallback callback


    @Override
    protected Task<List<String>> createTask() {
        return new GeneratorTask(configuration, callback)

    }

    @Override
    synchronized void restart() {
        //not support
    }

    synchronized void restart(Configuration configuration, ShellCallback callback) {
        if (!isRunning()) {
            this.configuration = configuration
            this.callback = callback
            super.restart()
        }
    }


    private static class GeneratorTask extends Task<List<String>> implements ProgressCallback {

        protected Logger logger = LoggerFactory.getLogger(this.getClass())

        private final Configuration configuration
        private final ShellCallback shellCallback
        private int saveTaskIndex = 1
        private int introspectionTaskIndex = 1
        private int generationTaskIndex = 1

        GeneratorTask(Configuration configuration, ShellCallback shellCallback) {
            this.configuration = configuration
            this.shellCallback = shellCallback
        }

        @Override
        protected List<String> call() throws Exception {
            List<String> warnings = new ArrayList<String>()
            def myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings)
            myBatisGenerator.generate(this)
            return warnings
        }

        @Override
        void introspectionStarted(int totalTasks) {
            updateMessage("正在保存文件${introspectionTaskIndex++}/${totalTasks}")
        }

        @Override
        void generationStarted(int totalTasks) {
            updateMessage("正在保存文件${generationTaskIndex++}/${totalTasks}")
        }

        @Override
        void saveStarted(int totalTasks) {
            updateMessage("正在保存文件${saveTaskIndex++}/${totalTasks}")
        }

        @Override
        void startTask(String taskName) {
            updateMessage("正在执行${taskName}...")
        }

        @Override
        void checkCancel() throws InterruptedException {
            updateMessage("取消生成")
        }

        @Override
        protected void succeeded() {
            List<String> warnings = getValue()
            if (warnings.isEmpty()) {
                updateMessage("生成成功")
            } else {
                String msg = warnings.join(";\r\n")
                updateMessage("警告：" + msg)
                System.err.println(msg)
            }
        }

        @Override
        void done() {

        }

        @Override
        protected void failed() {
            super.failed()
            def throwable = getException()
            updateMessage("生成失败, " + throwable.getMessage())
        }
    }

}
