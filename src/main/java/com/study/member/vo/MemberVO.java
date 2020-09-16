package com.study.member.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.study.common.valid.JoinStep2;
import com.study.common.valid.JoinStep3;
import com.study.common.valid.RegistType;

@SuppressWarnings("serial")
public class MemberVO implements Serializable {

	@NotBlank(message = "회원 아이디는 필수입니다.", groups = { Default.class, JoinStep2.class })
	@Pattern(regexp = "\\w{4,12}", message = "알파벳과 숫자, 언더바(_)로 4글자 이상 12글자 이하입니다.")
	private String memId; /* 회원 아이디 */

	@NotBlank(message = "회원 비밀번호는 필수입니다.", groups = { RegistType.class, JoinStep2.class })
	@Pattern(regexp = "[\\w!@#$%]{4,12}", message = "알파벳과 숫자, 언더바(_), 특수문자(!@#$%)로 4글자 이상 12글자 이하입니다.")
	private String memPass; /* 회원 비밀번호 */

	@NotBlank(message = "회원 이름은 필수입니다.", groups = { Default.class, JoinStep2.class })
	@Pattern(regexp = "[가-힣]{2,6}", message = "한글로 2글자 이상 6글자 이하입니다.", groups = { Default.class, JoinStep2.class })
	private String memName; /* 회원 이름 */

	@NotBlank(message = "회원 생일은 필수입니다.", groups = { Default.class, JoinStep3.class })
	@Pattern(regexp = "\\d{4}[-/.]\\d{2}[-/.]\\d{2}"
			 , message = "생일은 YYYY-MM-DD형식으로 입력해주세요."
			 , groups = { Default.class, JoinStep3.class })
	
	private String memBir; /* 회원 생일 */

	@NotBlank(message = "우편번호는 필수입니다.", groups = { Default.class, JoinStep3.class })
	@Pattern(regexp = "\\d{5,7}"
			 , message = "우편번호가 아닙니다."
			 , groups = { Default.class, JoinStep3.class })
	private String memZip; /* 우편번호 */

	
	@NotBlank(message = "기본주소는 필수입니다.", groups = { Default.class, JoinStep3.class })
	private String memAdd1; /* 주소 */

	@NotBlank(message = "상세주소는 필수입니다.", groups = { Default.class, JoinStep3.class })
	private String memAdd2; /* 상세주소 */

	private String memHp; /* 연락처 */

	@NotBlank(message = "이메일은 필수입니다.", groups = { Default.class, JoinStep2.class })
	@Email(message = "이메일 형식이 아닙니다.", groups = { Default.class, JoinStep2.class })
	private String memMail; /* 이메일 */

	//@NotBlank(message = "직업은 필수입니다.")
	private String memJob; /* 직업 코드 */
 
	//@NotBlank(message = "취미는 필수입니다.")
	private String memLike; /* 취미 코드 */

	private int memMileage; /* 마일리지 */
	private String memDelete; /* 탈퇴여부 */

	// 추가된 필드,
	private String memJobNm; /* 회원 직업 */
	private String memLikeNm;/* 회원 취미 */

//	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
//	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getMemPass() {
		return memPass;
	}

	public void setMemPass(String memPass) {
		this.memPass = memPass;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemBir() {
		return memBir;
	}

	public void setMemBir(String memBir) {
		this.memBir = memBir;
	}

	public String getMemZip() {
		return memZip;
	}

	public void setMemZip(String memZip) {
		this.memZip = memZip;
	}

	public String getMemAdd1() {
		return memAdd1;
	}

	public void setMemAdd1(String memAdd1) {
		this.memAdd1 = memAdd1;
	}

	public String getMemAdd2() {
		return memAdd2;
	}

	public void setMemAdd2(String memAdd2) {
		this.memAdd2 = memAdd2;
	}

	public String getMemHp() {
		return memHp;
	}

	public void setMemHp(String memHp) {
		this.memHp = memHp;
	}

	public String getMemMail() {
		return memMail;
	}

	public void setMemMail(String memMail) {
		this.memMail = memMail;
	}

	public String getMemJob() {
		return memJob;
	}

	public void setMemJob(String memJob) {
		this.memJob = memJob;
	}

	public String getMemLike() {
		return memLike;
	}

	public void setMemLike(String memLike) {
		this.memLike = memLike;
	}

	public int getMemMileage() {
		return memMileage;
	}

	public void setMemMileage(int memMileage) {
		this.memMileage = memMileage;
	}

	public String getMemDelete() {
		return memDelete;
	}

	public void setMemDelete(String memDelete) {
		this.memDelete = memDelete;
	}

	public String getMemJobNm() {
		return memJobNm;
	}

	public void setMemJobNm(String memJobNm) {
		this.memJobNm = memJobNm;
	}

	public String getMemLikeNm() {
		return memLikeNm;
	}

	public void setMemLikeNm(String memLikeNm) {
		this.memLikeNm = memLikeNm;
	}
}
