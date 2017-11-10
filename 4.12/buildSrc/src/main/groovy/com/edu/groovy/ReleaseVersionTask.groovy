package com.edu.groovy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction



//自定义Task的实现
class ReleaseVersionTask extends DefaultTask {

	//通过注解声明输入输出
	@Input Boolean release
	@OutputFile File destFile

	//构造方法
	ReleaseVersionTask(){
		group= 'versioning'
		description = 'Make Project a Release Version!'
	}

	void start() {
		
		version.release = true
		//Ant propertyfile 提供了一种便利的方式来修改属性文件
		ant.propertyfile (file : versionFile) {
			entry(key: 'release', type: 'string', operation : '=', value :'true')
		}
	}
}

