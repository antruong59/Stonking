package unisa.dse.a2.students;

public class UntradedCompanyException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UntradedCompanyException(String companyCode)
	{
		super(companyCode + " is not a listed company on this exchange");
	}
}
