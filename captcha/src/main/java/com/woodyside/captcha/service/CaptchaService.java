package com.woodyside.captcha.service;

import cn.apiclub.captcha.Captcha;
import com.woodyside.captcha.exception.NotFoundCaptchaIdException;
import com.woodyside.captcha.model.CaptchaEntity;
import com.woodyside.captcha.payload.request.GenCaptchaRequestBody;
import com.woodyside.captcha.payload.response.GenCaptchaResponseBody;
import com.woodyside.captcha.payload.response.ValidateCaptchaResponse;
import com.woodyside.captcha.repository.CaptchaRepository;
import com.woodyside.captcha.util.CaptchaUtlCreator;
import com.woodyside.captcha.util.UtilRandomStringGenerator;
import com.woodyside.services.captcha.payload.response.GetCaptchaResponseBody;
import com.woodyside.services.client.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;

import static com.woodyside.captcha.util.DateResponseFormatter.getTimestamp;

@Service
@AllArgsConstructor
public class CaptchaService {

    private final CaptchaRepository captchaRepository;

    private final ClientService clientService;

    public GenCaptchaResponseBody genCaptchaId(GenCaptchaRequestBody genCaptchaRequest) {
        clientService.findByEmail(genCaptchaRequest.getEmail());
        CaptchaEntity captcha = CaptchaEntity.builder()
                .isUsed(false)
                .validatedResult(false)
                .captchaId(UtilRandomStringGenerator.generate())
                .clientUsername(genCaptchaRequest.getEmail())
                .build();

        captchaRepository.save(captcha);

        return GenCaptchaResponseBody.builder().captchaId(captcha.getCaptchaId())
                .build();
    }

    public String drawCaptcha(String captchaId, HttpServletRequest request, HttpServletResponse response) {
        CaptchaEntity found = captchaRepository.findByCaptchaId(captchaId)
                .orElseThrow(NotFoundCaptchaIdException::new);

        Captcha captcha = CaptchaUtlCreator.createCaptcha(240, 70);

        try(OutputStream outputStream = response.getOutputStream()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("CAPTCHA", captcha);
            found.setIsUsed(true);
            found.setCodeHash(UtilRandomStringGenerator.toHash(captcha.getAnswer()));
            captchaRepository.save(found);
            ImageIO.write(captcha.getImage(), "jpeg", outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return String.valueOf(captcha.getImage());
    }

    public ValidateCaptchaResponse validateCaptcha(String captchaId, String captchaCode, HttpSession session) {

        Captcha captcha = (Captcha) session.getAttribute("CAPTCHA");

        return captchaRepository.findByCaptchaId(captchaId)
                .map(found -> {
                    found.setValidatedResult(captcha.getAnswer().equals(captchaCode));
                    found.setEnteredText(captchaCode);
                    captchaRepository.save(found);
                    return ValidateCaptchaResponse.builder()
                            .validateFlag(found.getValidatedResult())
                            .timestamp(getTimestamp())
                            .build();
                })
                .orElseThrow(NotFoundCaptchaIdException::new);
    }

    public GetCaptchaResponseBody findByCaptchaId(String captchaId) {
       CaptchaEntity found = captchaRepository.findByCaptchaId(captchaId)
               .orElseThrow(NotFoundCaptchaIdException::new);

       return GetCaptchaResponseBody.builder()
               .validatedResult(found.getValidatedResult())
               .email(found.getClientUsername())
               .timestamp(getTimestamp())
               .build();
    }
}
