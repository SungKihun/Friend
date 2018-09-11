package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.repository.ImgRepository;

@Component
public class UserFunction {
	
   @Autowired
   private ImgRepository imgRepository;	

    private final SimpMessagingTemplate template;
    public UserFunction(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	//리스트 맵을 맵으로 바꿔주는 함수
	public Map<String, Object> ListToMap(List<Map<String,Object>> list, String keyname){
		Map<String, Object> map= new HashMap<>(); 
		System.out.println("ListToMap:" + list.size());
		for(int j = 0 ; j < list.size(); j++) {
			String key = list.get(j).get(keyname).toString();
			map.put(key, list.get(j)); 
		}
		return map;
	}

	//리스트맵의 key가 두개 일경우
	public Map<String, Map<String, Object>> ListToMapDupl(List<Map<String, Object>> list, String firstkey, String secondkey){
		Map<String, Map<String, Object>> resultMap= new HashMap<>(); 
		System.out.println("ListToMapDupl:" + list.size());
		String  fkey,skey ;
		
		for(int j = 0 ; j < list.size(); j++) {
			fkey = list.get(j).get(firstkey).toString();
			skey = list.get(j).get(secondkey).toString();
			
			//추가할 자료
			Map<String, Object> subMap = new HashMap<>();
			subMap.put(skey, list.get(j));
			//기존 맵 가져오기
			Map<String, Object> oldMap = new HashMap<>();
			if ( resultMap.get(fkey) != null) {
				oldMap = resultMap.get(fkey);
			}
			
			//기존맵에 새로운 자료 추가
			oldMap.put(skey, list.get(j));
			
			//새로운맵 결과값에 넣기
			resultMap.put(fkey, oldMap);
			
		}
		return resultMap;
	}
	
	   //디폴트 메시지, 인트로 생성
	   public Map<String,Object> getDefaultMsg(){
	      List<String> defaultMsg = new ArrayList<>();
	      defaultMsg.add("안녕하세요. 반갑습니다.");
	      defaultMsg.add("행복한 하루 보내세요.");
	      defaultMsg.add("후후후");
	      
	      List<String> defaultIntro = new ArrayList<>();
	      defaultIntro.add("테스트입니다.");
	      defaultIntro.add("인트로입니다.");
	      
	      Map<String, Object> msg = new HashMap<>();
	      msg.put("defaultMsg", defaultMsg);
	      msg.put("defaultIntro", defaultIntro);
	      return msg;
	      
	   }

	   
	 //DB에서 불러온 경로값으로 파일을 읽어들여 바이너리 형식의 문자열로 반환함
	   public List<String> fileload(List<String> direc) throws IOException {

	      List<String> imgList = new ArrayList<>();
	      
	      Encoder encodeder = Base64.getEncoder();
	      ByteArrayOutputStream bos = null;
	      FileInputStream fis = null;
	      File file = null;
	      byte[] buffer = new byte[1024];
	      int b;
	      byte[] encoded = null;
	      byte[] fileBytes = null;
	      String encodedString = null;
	      
	      for(String fileName : direc) {
	         
	         bos = new ByteArrayOutputStream();
	         
	         file = new File(fileName);
	         fis = new FileInputStream(file);
	         
	         while((b = fis.read(buffer)) != -1)
	            bos.write(buffer, 0, b);
	         
	         fileBytes = bos.toByteArray();
	         
	         encoded = encodeder.encode(fileBytes);
	         encodedString = new String(encoded);
	         
	         System.out.println(encodedString);
	         
	         imgList.add(encodedString);
	         bos.flush();
	      }
	      fis.close();
	      bos.close();
	      
	      return imgList;
	   }	   
	   
	   // 파일 저장을 위한 메소드
//	   public void saveFile(MultipartFile[] files, List<Boolean> mainImgYN, int userid, int gubun) throws IOException {
//	      String fileName = null;
//	      for(int i = 0; i < files.length; i++) {
//	    	  if(!files[i].getOriginalFilename().equals("")) {
//		         fileName = UUID.randomUUID() + files[i].getOriginalFilename();
//		         File saveFile = new File("c:/temp/friendimg/" + fileName);
//		         imgRepository.insertImgList(gubun, userid, fileName, mainImgYN.get(i));
//		         files[i].transferTo(saveFile);
//	    	  }
//	      }
//	   }
	   
	   //알람 소켓으로 전송
//	   public void sendAlarmSocket(Alarm alarm) {
//		   System.out.println(alarm.getUserid());
//		   System.out.println(alarm.getMessage());
//   			template.convertAndSend("/topic/"+alarm.getUserid(), alarm);
//	   }
	   
	 //Date정보를받으면 나이구하기
	   public int UserAge(Date date) {
	      Date d=new Date();
	      int age=d.getYear()-date.getYear()+1;
	      return age;
	   }
	   //나이정보를받으면 Date구하기
	   public Date UserDate(int age) {
	      Date nowDate=new Date();
	      nowDate.setYear(nowDate.getYear()-age+1);
	      System.out.println("nowDate : "+nowDate);
	      return nowDate;
	   }

}