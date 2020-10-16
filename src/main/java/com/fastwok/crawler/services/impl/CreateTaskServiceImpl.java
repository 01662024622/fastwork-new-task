package com.fastwok.crawler.services.impl;

import com.fastwok.crawler.entities.User;
import com.fastwok.crawler.repository.UserRepository;
import com.fastwok.crawler.services.BaseService;
import com.fastwok.crawler.services.isservice.CreateTaskService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CreateTaskServiceImpl implements CreateTaskService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void getData() throws UnirestException, MessagingException {
        Date date = new Date();
        Date tomorrow = new Date(date.getTime() - 986400000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = formatter.format(tomorrow);
        List<User> users=userRepository.getByDate(strDate);
        log.info(users.toString());
        long timeMilli = date.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String startDate = formatter.format(date);
        for (User user : users){
            String string = getBody(user,startDate,timeMilli);
            createTask(string,timeMilli);
        }
        log.info(strDate);
    }

    private HttpResponse<JsonNode> createTask(String string,Long timeMilli)
            throws UnirestException {
        return Unirest.post("https://work.fastwork.vn:6014/Task/5efef3dd5a51cf1c10fab0e4")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "work.fastwork.vn:6014")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body(string)
                .asJson();
    }
    private String getBody(User user,String date,Long from){
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String strDate = formatter.format(date);
//        String dateWithoutTime = strDate.toString().substring(0, 10);
        return "{\"worktype\":{}," +
                "\"score\":0," +
                "\"name\":\"BC 360/"+user.getName()+"/"+date+"\"," +
                "\"description\":\"\"," +
                "\"assignTo\":[{\"email\":\""+user.getEmailFastwork()+"\",\"name\":\""+user.getName()+"\",\"avatar\":\"\"}]," +
                "\"followers\":[]," +
                "\"priority\":\"Trung bình\"," +
                "\"customers\":[]," +
                "\"contacts\":[]," +
                "\"forms\":[]," +
                "\"project\":{}," +
                "\"checklists\":[]," +
                "\"parent\":\"\"," +
                "\"start_date\":"+from+"," +
                "\"end_date\":"+(from+172800000)+"," +
                "\"start\":\"\"," +
                "\"start_type\":\"no\"," +
                "\"deadline\":\"\"," +
                "\"deadline_type\":\"auto\"," +
                "\"members\":[{\"email\":\"assignTo\",\"name\":\"Người thực hiện\"}]," +
                "\"danh_gia\":false," +
                "\"viewMode\":\"protected\"," +
                "\"urgent\":false}";
    }
}
