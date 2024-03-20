package com.macrace.angidaybe.services.impl;

import com.macrace.angidaybe.config.SoapConfig;
import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
import com.macrace.angidaybe.dto.response.ForgetPasswordResponse;
import com.macrace.angidaybe.services.UserService;
import com.macrace.angidaybe.tasks.SendSmsTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends SoapConfig implements UserService {
    private final TaskExecutor executor;

    @Override
    public ForgetPasswordResponse sentOtp(ForgotPasswordRequest request) {
        String message = "Ma OTP cua ban la "
                + generateOTP() +
                " hieu luc trong 1 phut. Khong duoc chia se ma nay voi nguoi khac";

//        String xml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:impl=\"http://impl.bulkSms.ws/\">\r\n" +
//                "       <soapenv:Header/>\r\n" +
//                "       <soapenv:Body>\r\n" +
//                "         <impl:wsCpMt>\r\n" +
//                "           <User>" + getUser() + "</User>\r\n" +
//                "           <Password>" + getPassword() + "</Password>\n" +
//                "           <CPCode>" + getCpCode() + "</CPCode>\r\n" +
//                "           <RequestID>1</RequestID>\r\n" +
//                "           <UserID>" + "84" + request.getPhoneNumber().substring(1, 10) + "</UserID>\r\n" +
//                "           <ReceiverID>" + "84" + request.getPhoneNumber().substring(1, 10) + "</ReceiverID>\r\n" +
//                "           <ServiceID>" + getBrandName() + "</ServiceID>\r\n" +
//                "           <CommandCode>bulksms</CommandCode>\r\n" +
//                "           <Content>" + message + "</Content>\r\n" +
//                "           <ContentType>0</ContentType>\r\n" +
//                "         </impl:wsCpMt>\r\n" +
//                "       </soapenv:Body>\r\n" +
//                "     </soapenv:Envelope>";
//
//        String response = callSoapService(xml);
//        log.info(response);
        executor.execute(new SendSmsTask(
                getEndpoint(),
                getUser(),
                getPassword(),
                getCpCode(),
                getBrandName(),
                request.getPhoneNumber(),
                message
        ));
        return new ForgetPasswordResponse("Your OTP was sent. Please wait");
    }

    private String generateOTP() {
        int randomPin = (int) ((Math.random() * 9000) + 1000);
        return String.valueOf(randomPin);
    }

    private String callSoapService(String soapRequest) {
        try {
            URL url = new URL(getEndpoint());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(soapRequest);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
