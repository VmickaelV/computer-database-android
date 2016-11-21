package com.excilys.mviegas.computer_database.android.dto;

import com.excilys.mviegas.computer_database.android.data.Company;
import com.excilys.mviegas.computer_database.android.data.Computer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.threeten.bp.LocalDate;

/**
 * DTO d'un computer d'un formulaire.
 *
 * @author VIEGAS Mickael
 */
public class ComputerDto {

    //=============================================================
    // Attributs - private
    //=============================================================
    private long mId;
    private String mName;
    private LocalDate mIntroducedDate;
    private LocalDate mDiscontinuedDate;
    private long mIdCompany;
	private Company mCompany;

    //=============================================================
    // Constructeurs
    //=============================================================
    public ComputerDto() {
    }

    public ComputerDto(long mId, String mName, LocalDate mIntroducedDate, LocalDate mDiscontinuedDate, long mIdCompany) {
        this.mId = mId;
        this.mName = mName;
        this.mIntroducedDate = mIntroducedDate;
        this.mDiscontinuedDate = mDiscontinuedDate;
        this.mIdCompany = mIdCompany;
    }

    public ComputerDto(Computer pComputer) {
        mId = pComputer.getId();
        mName = pComputer.getName();

        if (pComputer.getIntroducedDate() != null) {
            mIntroducedDate = pComputer.getIntroducedDate();
        }

        if (pComputer.getDiscontinuedDate() != null) {
            mDiscontinuedDate = pComputer.getDiscontinuedDate();
        }

        if (pComputer.getManufacturer() != null) {
            mIdCompany = pComputer.getManufacturer().getId();
        }
    }

    //=============================================================
    // Getters & Setters
    //=============================================================

    public long getId() {
        return mId;
    }

    public void setId(long pId) {
        mId = pId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String pName) {
        mName = pName;
    }

    public LocalDate getIntroducedDate() {
        return mIntroducedDate;
    }

    public void setIntroducedDate(LocalDate mIntroducedDate) {
        this.mIntroducedDate = mIntroducedDate;
    }

    public LocalDate getDiscontinuedDate() {
        return mDiscontinuedDate;
    }

    public void setDiscontinuedDate(LocalDate mDiscontinuedDate) {
        this.mDiscontinuedDate = mDiscontinuedDate;
    }

    public long getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(long pIdCompany) {
        mIdCompany = pIdCompany;
    }

    @JsonIgnore
    public Company getCompany() {
        return mCompany;
    }

    @JsonIgnore
    public void setCompany(Company company) {
        mCompany = company;
    }

	//=============================================================
    // toString
    //=============================================================

	@Override
	public String toString() {
		return "ComputerDto{" +
				"mCompany=" + mCompany +
				", mId=" + mId +
				", mName='" + mName + '\'' +
				", mIntroducedDate=" + mIntroducedDate +
				", mDiscontinuedDate=" + mDiscontinuedDate +
				", mIdCompany=" + mIdCompany +
				'}';
	}
}
