package com.eworld.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotBlank(message = "họ và tên không được bỏ trống")
    @Size(min = 3, max = 20, message = "họ và tên có ít nhất 3 ký tự và tối đa 20 ký tự")
    private  String fullName;
    @NotBlank(message = "Email không được bỏ trống")
    @Email(message = "Email không đúng định dạng")
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    @NotBlank(message = "tiêu đề không được bỏ trống")
    @Size(min = 3, max = 50, message = "tiêu đề có ít nhất 3 ký t và tối đa 50 ký tự")
    private String mailSubject;
    @NotBlank(message = "nội dung phản hồi không được bỏ trống")
    @Size(min = 3, max = 255, message = "nội dung có ít nhất 3 ký tự và tối đa 255 ký tự")
    private String mailContent;
    private String contentType = "text/plain";
    private List<Object> attachments;

    public Date getMailSendDate() {
        return new Date();
    }
}
