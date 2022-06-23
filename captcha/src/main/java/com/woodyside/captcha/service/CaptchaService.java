package com.woodyside.captcha.service;

import cn.apiclub.captcha.Captcha;
import com.woodyside.captcha.exception.NotFoundCaptchaIdException;
import com.woodyside.captcha.model.CaptchaEntity;
import com.woodyside.captcha.payload.request.GenCaptchaRequestBody;
import com.woodyside.captcha.payload.response.GenCaptchaResponseBody;
import com.woodyside.captcha.repository.CaptchaRepository;
import com.woodyside.captcha.util.CaptchaUtlCreator;
import com.woodyside.captcha.util.UtilRandomStringGenerator;
import com.woodyside.services.client.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.Optional;

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
        Optional<CaptchaEntity> found = Optional.ofNullable(captchaRepository.findByCaptchaId(captchaId)
                .orElseThrow(NotFoundCaptchaIdException::new));

        Captcha captcha = CaptchaUtlCreator.createCaptcha(240, 70);

        try(OutputStream outputStream = response.getOutputStream()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("CAPTCHA", captcha);
            found.get().setIsUsed(true);
            found.get().setCodeHash(UtilRandomStringGenerator.toHash(captcha.getAnswer()));
            captchaRepository.save(found.get());
            ImageIO.write(captcha.getImage(), "jpeg", outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return String.valueOf(captcha.getImage());
    }
}
