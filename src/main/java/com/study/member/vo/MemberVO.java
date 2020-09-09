package com.study.member.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.study.common.valid.RegistType;

public class MemberVO {
	
	@NotBlank(message = "회원 아이디는 필수입니다.")
	@Pattern(regexp = "\\w{4,12}", message = "알파벳과 숫자, 언더바(_)로 4글자 이상 12글자 이하입니다.")
	private String memId;         /* 회원 아이디 */
	
	@NotBlank(message = "회원 비밀번호는 필수입니다.", groups = RegistType.class)
	@Pattern(regexp = "[\\w!@#$%]{4,12}", message = "알파벳과 숫자, 언더바(_), 특수문자(!@#$%)로 4글자 이상 12글자 이하입니다.")
	private String memPass;       /* 회원 비밀번호 */
	
	@NotBlank(message = "회원 이름은 필수입니다.")
	@Pattern(regexp = "[가-힣]{2,}", message = "한글로 2글자 이상입니다.")
	private String memName;       /* 회원 이름 */
	
	private String memBir;        /* 회원 생일 */
	
	@NotBlank(message = "우편번호는 필수입니다.")
	@Pattern(regexp = "\\d{5,7}", message = "우편번호가 아닙니다.")
	private String memZip;        /* 우편번호 */
	
	@NotBlank(message = "주소는 필수입니다.")
	private String memAdd1;       /* 주소 */
	
	@NotBlank(message = "상세주소는 필수입니다.")
	private String memAdd2;       /* 상세주소 */
	
	private String memHp;         /* 연락처 */
	
	@Email(message = "이메일 형식이 아닙니다.")
	private String memMail;       /* 이메일 */
	
	@NotBlank(message = "직업은 필수입니다.")
	private String memJob;        /* 직업 코드 */
	
	@NotBlank(message = "취미는 필수입니다.")
	private String memLike;       /* 취미 코드 */
	
	private int memMileage;       /* 마일리지 */
	private String memDelete;     /* 탈퇴여부 */
	
	
	// 추가된 필드,
	private String memJobNm; /* 회원 직업*/
	private String memLikeNm;/* 회원 취미*/
	
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
