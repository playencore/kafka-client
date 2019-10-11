package com.charida.kafka.hadoop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HdfsCommander {
	@Autowired
	private HadoopConf conf;
	private final String Prefix = "data";
	/**
	 * HDFS 파일 업로드
	 * 
	 * @param makeDir - 디렉토리 생성 여부
	 * @param dirPath - 저장할 경로
	 * @param msg  -  저장할 텍스트
	 */
	public void uploadFileToHdfs(boolean makeDir, String dirPath
			, String msg) {
		
		if(dirPath== null || msg == null) return;
		
		if(makeDir) {
			createDirectory(dirPath);
		}
		if(dirPath.charAt(dirPath.length()-1) != '/') {
			dirPath += "/";
		}
		String fileName = makeFileName();
		
		if(!isExists(dirPath,fileName)) {
			writeFile(dirPath,fileName,msg);
		}else {
			appendFile(dirPath,fileName,msg);
		}
		
	}
	/**
	 *  일별 파일 이름 생성 (data-year-month-day) 
	 * 
	 * @return fileName
	 */
	private String makeFileName() {
		StringBuffer name= new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		
		name.append(Prefix);
		name.append("-"+(calendar.get(GregorianCalendar.YEAR)));
		name.append("-"+(calendar.get(GregorianCalendar.MONTH)+1));
		name.append("-"+calendar.get(GregorianCalendar.DAY_OF_MONTH));
		
		return name.toString();
	}
	/**
	 * 파일이 존재 여부 확인
	 * 
	 * @param dirPath 디렉터리 경로
	 * @param fileName 파일명
	 * @return boolean
	 */
	private boolean isExists(String dirPath,String fileName) {
		boolean exist = false;
		
		try (FileSystem fileSystem = FileSystem.get(conf.getConf())) {
			Path path = new Path(dirPath+fileName);
			exist = fileSystem.exists(path);
		} catch (IOException e) {
			System.out.println("exist error");
		}
		
		return exist;
	}
	/**
	 *  디렉터리 생성
	 * 
	 * @param dirPath 디렉터리 경로
	 */
	private void createDirectory(String dirPath) {
		
		try (FileSystem fileSystem = FileSystem.get(conf.getConf())) {
			Path path = new Path(dirPath);
			if (!fileSystem.exists(path)) {
				fileSystem.mkdirs(path);
			}
		} catch (IOException e) {
			System.out.println("make dir error");
		}

	}
	/**
	 * 
	 * HDFS에 파일 생성 및 msg 출력
	 * 
	 * @param dirPath 디렉터리 경로
	 * @param fileName 파일명
	 * @param msg 파일에 써질 txt
	 */
	private void writeFile(String dirPath, String fileName, String msg) {

		Path hdfsWritePath = new Path(dirPath + fileName);
		
		try(
			FileSystem fileSystem = FileSystem.get(conf.getConf());
			FSDataOutputStream	fsDataOutputStream = fileSystem.create(hdfsWritePath, true);
			BufferedWriter	bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
		){
			bufferedWriter.write(msg);
			bufferedWriter.newLine();
		}catch (IOException e) {
			System.out.println("file write error");
		}
	

	}
	/**
	 * 생성된 파일에 txt 추가
	 * 
	 * @param dirPath 디렉터리 경로
	 * @param fileName 파일명
	 * @param msg txt
	 */
	private void appendFile(String dirPath, String fileName, String msg) {
		Path hdfsWritePath = new Path(dirPath + fileName);
		
		try(
			FileSystem fileSystem = FileSystem.get(conf.getConf());
			FSDataOutputStream fsDataOutputStream = fileSystem.append(hdfsWritePath);
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
		){
			bufferedWriter.write(msg);
			bufferedWriter.newLine();
			bufferedWriter.close();
			fsDataOutputStream.close();
			fileSystem.close();
		}catch (IOException e) {
			System.out.println("file append error");
		}
	}
}
