package com.edu.groovy

import org.gradle.api.Project
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

class NotificationTestListener implements TestListener{

    final Project project
    NotificationTestListener(Project project){
        this.project = project
    }

    @Override
    void beforeSuite(TestDescriptor suite) {}

    @Override
    void afterSuite(TestDescriptor suite, TestResult result) {
        if (!suite.parent && result.getEndTime() > 0){
            long elapsedTestTime = result.getEndTime() - result.getStartTime()
            suite.announce("""Elapsed time for excution of ${result.getTestCount()} test(s): $elapsedTestTime ms""",'local')
        }
    }

    @Override
    void beforeTest(TestDescriptor testDescriptor) {}

    @Override
    void afterTest(TestDescriptor testDescriptor, TestResult result) {}
}