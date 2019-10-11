package com.charida.kafka.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HadoopConf {
	
	private final Configuration conf;
	/**
	 * HADOOP 설정파일
	 * 
	 * @param coreConfPath core-site.xml 경로
	 * @param hdfsConfPath hdfs-site.xml 경로
	 */
	public HadoopConf(@Value("${custom.hadoop.core}")String coreConfPath
			,@Value("${custom.hadoop.hdfs}")String hdfsConfPath) {
		conf = new Configuration();
		conf.addResource(new Path(coreConfPath));
		conf.addResource(new Path(hdfsConfPath));
	}
	
	public Configuration getConf() {
		return conf;
	}
}
