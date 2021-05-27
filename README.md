### JMeter+Maven+Jenkins无人值守自动化测试框架

##### 一、JMeter介绍
- 具体参考[JMeter官网](https://jmeter.apache.org/)
###### 1.1、环境准备
- jdk必不可少，1.8+
- jmeter安装包，选择合适的版本即可，[下载二进制安装包地址](https://archive.apache.org/dist/jmeter/binaries/)
###### 1.2、工具使用
- GUI模式，多用于调试，不管是开发接口测试脚本或是性能测试脚本，皆是如此；
- NGUI模式，多用于测试执行，尤其是性能测试，nGUI模式会节约负载机资源；
- - CLI模式用于集成Jenkins CI/CD
###### 1.3、脚本开发
- 首要事情是必先熟悉工具元件的作用
- - 功能及作用域、执行顺序
- 使用工具的目的
- - 用于接口自动化
- - 用于性能自动化
- - 用于UI功能自动化
- 对于不同的测试目的，提供对应文案
- - 接口文档
- - 性能测试需求文档
- - UI自动化测试设计方案
##### 二、Maven介绍
- 具体参考[Maven官网](http://maven.apache.org/index.html)
###### 2.1、构建工具的选择
- ant
- - jmeter源码5.4以前都是使用ant工具构建应用；而后改用了gradle
- - 理应使用ant更好的兼容及搭配使用；对于二次开发需要注册的jar比较友好但且复杂
- maven
- - 只因更好的构建而更加清晰的管理jar包，有一个maven中央仓库，使用jmeter只需引用一个jar即可
```xml
<build>
		<plugins>
			<plugin>
				<!-- 核心插件，用来执行jmx脚本，注意版本号，2.1.0可以使用用jmeter3.1生成的脚本。最新的2.2.0使用jmeter3.2生成的脚本 -->
				<groupId>com.lazerycode.jmeter</groupId>
				<artifactId>jmeter-maven-plugin</artifactId>
				<version>2.5.1</version>
				<configuration>

					<!--报告文件尾部输出时间 -->

					<!-- 默认报告优化，修改jmeter.properties配置 -->
					<propertiesJMeter>
						<log_level.jmeter>DEBUG</log_level.jmeter>
					</propertiesJMeter>
					<resultsFileFormat>xml</resultsFileFormat>
					<ignoreResultFailures>true</ignoreResultFailures>
					<testResultsTimestamp>false</testResultsTimestamp>
					<!-- 如果不想查看jmeter生成的log,设置为true -->
					<suppressJMeterOutput>true</suppressJMeterOutput>
					<!-- 增加扩展插件 -->
					<jmeterExtensions>
						<!--此处自己引入的jar包 -->
					</jmeterExtensions>

					<!-- 增加jar包,需要先将jar注册到本地maven仓库，打开cmd使用如下命令 -->
					<!--mvn install:install-file -Dfile=D:\GIT\ZyzxAPIAutoTest\TXPTAPIAutoTest\plugns\jmeter-plugins-json.jar 
						-DgroupId=com.jmeter.chajian -DartifactId=jmeter-plugins-json -Dversion=2.6 
						-Dpackaging=jar -->
					<!-- <jmeterExtensions> -->
					<!-- jmeter扩展插件 json path assert -->
					<!-- <artifact>com.atlantbh.jmeter:jmeter-plugins-json:2.7</artifact> -->
					<!-- 本地自己写的jar -->
					<!-- <artifact>com.smrz:smrz-utils:1.0</artifact> -->
					<!-- </jmeterExtensions> -->

					<!-- 设置jmeter生成结果文件格式 -->
					<resultsFileFormat>xml</resultsFileFormat>
					<!-- 设置忽略失败是否停止运行 -->
					<ignoreResultFailures>true</ignoreResultFailures>
					<!--设置结果是否有时间戳,与appendResultsTimestamp 或resultsFileNameDateFormat 搭配使用 -->
					<testResultsTimestamp>false</testResultsTimestamp>
					<testFilesIncluded>
						<!-- 使用<jMeterTestFiles>选择执行用例 -->
						<!-- <jMeterTestFile>*.jmx</jMeterTestFile> -->
						<!-- 标签排除用例 -->
						<excludeJMeterTestFile>*.jmx</excludeJMeterTestFile>
					</testFilesIncluded>
					<resultsDirectory>${jmeter.result.jtl.dir}</resultsDirectory>
					<!-- 使用<testFilesDirectory>指定测试用例的路径 -->
					<!-- <testFilesDirectory>/scratch/testfiles/</testFilesDirectory> -->
				</configuration>

				<executions>
					<execution>
						<id>jmeter-tests</id>
						<phase>verify</phase>
						<!--脚本所在的文件夹 -->
						<goals>
							<goal>jmeter</goal>
						</goals>
						<configuration>
							<appendResultsTimestamp>true</appendResultsTimestamp>
							<skipTests>${skipTests}</skipTests>
						</configuration>
					</execution>
				</executions>
			</plugin>

```
- 既然选择maven，就需要一个IDE开发工具更好的在本地完成脚本开发
##### 三、Jenkins介绍
- 具体参考[Jenkins国内版](https://www.w3cschool.cn/jenkins/)
###### 3.1、不能忘记的工具
- github、gitlab、各种代码托管云仓库
- 整个自动化测试框架从工具的选择到环境部署、脚本开发到调试、完善，再到代码托管、最后到自动化构建执行，集成在jenkins
- - 所以，在jenkins这个工具上，我们学会了很多，不要单独去开发一些工具脚本，譬如发邮件、报告展示等，这些都有插件在jenkins被支持；
###### 3.2、持续集成
- jenkins部署安装
- - jar包绿色执行
- - tomcat容器
- - docker部署
- 再安装所需要的插件
- - 通知插件、邮件配置、报告展示、构建工具等等
- 创建job
- - 设置触发器
- - 从更新代码、构建执行、报告输出、展示、通知；
- - 自动化测试最大的ROE来自于它的无人值守及使用率。

##### 四、JMeter+Maven+github+Jenkins框架实战
###### 4.1、工程结构
- src/test/jmeter放置jmeter需要的配置文件及脚本，当然也可以指定目录，可以在pom中修改执行目录
- src/test/jmeter/datas涉及数据启动的文件建议放在与目录同根目录
- src/test/resources放置其他资源文件，如报告模版
- pom根配置需要结合jenkins，记得要预留它的参数引用
###### 4.2、框架模式
- 本工程示例采用关键字驱动实现脚本开发
- 当然，可以分模块来开发.jmx脚本，使用不同的数据文件驱动
###### 4.3、二次开发
- 不管是开发sampler、还是函数，都是需要继承对应的依赖；pom需要配置依赖
```
<!-- 这是函数的依赖jar -->
<dependency>
    <groupId>org.apache.jmeter</groupId>
    <artifactId>ApacheJMeter_functions</artifactId>
    <version>5.3</version>
</dependency>

<!-- 这是开发java sampler依赖的核心jar -->
<dependency>
    <groupId>org.apache.jmeter</groupId>
    <artifactId>ApacheJMeter_core</artifactId>
    <version>5.3</version>
</dependency>

<dependency>
    <groupId>org.apache.jmeter</groupId>
    <artifactId>ApacheJMeter_java</artifactId>
    <version>5.3</version>
</dependency>
```
- 由于忘记了某些使用jmeter的场景，譬如：需要自己写加密函数、处理数据的方法等等功能，自己写好java然后注册到jmeter运行环境，调试时直接调用jar即可
- - 在src/test/java开发java功能
- - 再pom配置maven打包工具放在target/jmeter/lib/ext目标目录下
- - 如果实在本地调用，不再工程中，还是需要将这个jar移动到本地jmeter环境调试