package com.jean.mybatis.generator.core;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinshubao
 */
public class GeneratorService extends Service<List<String>> {

    private Configuration configuration;
    private ShellCallback callback;


    @Override
    protected Task<List<String>> createTask() {
        return new GeneratorTask(configuration, callback);
    }

    @Override
    public void restart() {
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
            updateMessage("introspection started, total tasks:" + totalTasks);
        }

        @Override
        public void generationStarted(int totalTasks) {
            updateMessage("generation started, total tasks:" + totalTasks);
        }

        @Override
        public void saveStarted(int totalTasks) {
            updateMessage("save started, total tasks:" + totalTasks);
        }

        @Override
        public void startTask(String taskName) {
            updateMessage(taskName);
        }

        @Override
        public void checkCancel() throws InterruptedException {
            if (this.isCancelled()) {
                updateMessage("CANCELLED");
                throw new InterruptedException("task was cancelled.");
            }
        }

        @Override
        protected void succeeded() {
            updateMessage("SUCCESS");
        }

        @Override
        public void done() {

        }

        @Override
        protected void failed() {
            Throwable throwable = getException();
            if (throwable != null) {
                updateMessage("failed, " + throwable.getMessage());
            } else {
                updateMessage("failed");
            }
        }
    }
}
