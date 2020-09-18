package com.study.question.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.study.common.valid.ModifyType;

@SuppressWarnings("serial")
public class QuestionBoardVO implements Serializable {

	@NotNull(message = "글 번호는 필수입니다.", groups = ModifyType.class)
	@Positive(message = "글 번호가 비었습니다.", groups = ModifyType.class)
	private int boNo; /* 글 번호 */

	@NotBlank(message = "제목은 필수입니다.")
	@Size(min = 1, message = "최소 한 글자 이상 입력하세요.")
	private String boTitle; /* 글 제목 */

	@NotBlank(message = "글 분류는 필수입니다.")
	private String boCategory; /* 글 분류 코드 */

//	@NotBlank(message = "작성자는 필수입니다.")
//	@Pattern(regexp = "[가-힣]{2,}", message = "한글 2글자 이상입니다.")
	private String boWriter; /* 작성자명 */

//	@NotBlank(message = "비밀번호는 필수입니다.")
//	@Pattern(regexp = "\\w{4,16}", message = "알파벳, 숫자, 언더바(_) 4글자 이상 16글자 이하입니다.")
	private String boPass; /* 비밀번호 */
	private String boContent; /* 글 내용 */
	private String boRegDate; /* 등록 일자 */
	private String boModDate; /* 수정 일자 */
	
//	@NotBlank
	private String boId;
	//private String boDelYn; /* 삭제 여부 */

	// 추가된 필드,
	private String boCategoryNm; /* 글 분류 명 */

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getBoNo() {
		return boNo;
	}

	public void setBoNo(int boNo) {
		this.boNo = boNo;
	}

	public String getBoTitle() {
		return boTitle;
	}

	public void setBoTitle(String boTitle) {
		this.boTitle = boTitle;
	}

	public String getBoCategory() {
		return boCategory;
	}

	public void setBoCategory(String boCategory) {
		this.boCategory = boCategory;
	}

	public String getBoWriter() {
		return boWriter;
	}

	public void setBoWriter(String boWriter) {
		this.boWriter = boWriter;
	}

	public String getBoPass() {
		return boPass;
	}

	public void setBoPass(String boPass) {
		this.boPass = boPass;
	}

	public String getBoContent() {
		return boContent;
	}

	public void setBoContent(String boContent) {
		this.boContent = boContent;
	}

	public String getBoRegDate() {
		return boRegDate;
	}

	public void setBoRegDate(String boRegDate) {
		this.boRegDate = boRegDate;
	}

	public String getBoModDate() {
		return boModDate;
	}

	public void setBoModDate(String boModDate) {
		this.boModDate = boModDate;
	}

	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}

	public String getBoCategoryNm() {
		return boCategoryNm;
	}

	public void setBoCategoryNm(String boCategoryNm) {
		this.boCategoryNm = boCategoryNm;
	}


}
