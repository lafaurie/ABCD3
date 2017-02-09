package cu.uci.abcd.circulation.ui.listener;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cu.uci.abcd.circulation.ui.model.EditorAreaUserLoan;
import cu.uci.abcd.domain.circulation.CirculationNomenclator;
import cu.uci.abcd.domain.common.Nomenclator;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.UiUtils;

public class EventEditLoanUserType implements Listener {
	private static final long serialVersionUID = 1L;
	public EditorAreaUserLoan clase;
	private Composite registerLoanUser;
	private Composite compoLoanUserTypeStudents;
	private Composite compoLoanUserTypeProfessor;
	private Composite compoLoanUserTypePostgraduate;
	private Composite compoLoanUserTypeLibrarian;
	private Composite compoLoanUserTypeLoanInterLibrarian;
	private Composite compoOther;
	private ContributorPage page;
	private Combo comboUserType;
	
	
	public EventEditLoanUserType(Combo comboUserType,Composite registerLoanUser,Composite compoLoanUserTypeStudents,
			Composite compoLoanUserTypeProfessor,Composite compoLoanUserTypePostgraduate, Composite compoLoanUserTypeLibrarian, 
			Composite compoLoanUserTypeLoanInterLibrarian, Composite compoOther
			,ContributorPage page) {
		super();
		this.comboUserType = comboUserType;
		this.registerLoanUser = registerLoanUser;
		this.compoLoanUserTypeStudents = compoLoanUserTypeStudents;
		this.compoLoanUserTypeProfessor = compoLoanUserTypeProfessor;
		this.compoLoanUserTypePostgraduate = compoLoanUserTypePostgraduate;
		this.compoLoanUserTypeLibrarian = compoLoanUserTypeLibrarian;
		this.compoLoanUserTypeLoanInterLibrarian = compoLoanUserTypeLoanInterLibrarian;
		this.compoOther = compoOther;
		this.page = page;
		
	}
//FIXME METODO COMPLEJO
	@Override
	public void handleEvent(Event arg0) {
	
		//int selectedIndexUserType = comboUserType.getSelectionIndex();
		Nomenclator loanUserType =  (Nomenclator) UiUtils.getSelected(comboUserType);

		if (loanUserType != null) {			
		
		if ( loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT)) {
			compoLoanUserTypeStudents.setVisible(true);				
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
						
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
			
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
			
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
			
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.getShell().getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().getShell().redraw();
			compoLoanUserTypeStudents.getShell().getShell().update();
			
			compoLoanUserTypeProfessor.getShell().getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().getShell().redraw();
			compoLoanUserTypeProfessor.getShell().getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
			
			compoOther.getShell().getShell().layout(true, true);
			compoOther.getShell().getShell().redraw();
			compoOther.getShell().getShell().update();
		
			
		} else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR) ||
				loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_EXECUTIVE) || 
				loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_OTHER_WORKERS)|| 
				loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_INVESTIGATOR)
				) {
			
			compoLoanUserTypeProfessor.setVisible(true);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
			
			compoLoanUserTypeStudents.setVisible(false);
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
			
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
			
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
		
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().redraw();
			compoLoanUserTypeStudents.getShell().update();
			
			compoLoanUserTypeProfessor.getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().redraw();
			compoLoanUserTypeProfessor.getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
	
			compoOther.getShell().layout(true, true);
			compoOther.getShell().redraw();
			compoOther.getShell().update();
					
			
		} else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE)) {
			compoLoanUserTypePostgraduate.setVisible(true);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
			
			compoLoanUserTypeStudents.setVisible(false);
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
			
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
				
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
		
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().redraw();
			compoLoanUserTypeStudents.getShell().update();
			
			compoLoanUserTypeProfessor.getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().redraw();
			compoLoanUserTypeProfessor.getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
	
			compoOther.getShell().layout(true, true);
			compoOther.getShell().redraw();
			compoOther.getShell().update();
		
			
		}else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN)) {
			compoLoanUserTypeLibrarian.setVisible(true);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeStudents.setVisible(false);
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
			
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
				
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
		
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().redraw();
			compoLoanUserTypeStudents.getShell().update();
			
			compoLoanUserTypeProfessor.getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().redraw();
			compoLoanUserTypeProfessor.getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
	
			compoOther.getShell().layout(true, true);
			compoOther.getShell().redraw();
			compoOther.getShell().update();
		
			
		} 
		else if (loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY)) {
		
			compoLoanUserTypeStudents.setVisible(false);
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
		
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
				
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
		
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
		
			compoLoanUserTypeLoanInterLibrarian.setVisible(true);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
		
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
		
			compoLoanUserTypeLoanInterLibrarian.getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().update();

			compoLoanUserTypeStudents.getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().redraw();
			compoLoanUserTypeStudents.getShell().update();
			
			compoLoanUserTypeProfessor.getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().redraw();
			compoLoanUserTypeProfessor.getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().update();
			
		
			compoOther.getShell().layout(true, true);
			compoOther.getShell().redraw();
			compoOther.getShell().update();
		} 
		else if (!(loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_STUDENT)) || !(loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_PROFESOR))
				|| !(loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_POSTGRADUATE))|| !(loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LIBRARIAN))
				|| !(loanUserType.getNomenclatorID().equals(CirculationNomenclator.LOANUSER_TYPE_LOAN_INTERLIBRARY))) {
			
			compoOther.setVisible(true);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.setVisible(false);
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
			
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
			
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);		
			
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
		
			compoLoanUserTypeStudents.getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().redraw();
			compoLoanUserTypeStudents.getShell().update();
			
			compoLoanUserTypeProfessor.getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().redraw();
			compoLoanUserTypeProfessor.getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
	
			compoOther.getShell().layout(true, true);
			compoOther.getShell().redraw();
			compoOther.getShell().update();		
		}
	}
		else{
			compoLoanUserTypeStudents.setVisible(false);				
			page.insertComposite(compoLoanUserTypeStudents, registerLoanUser);
						
			compoLoanUserTypeProfessor.setVisible(false);
			page.insertComposite(compoLoanUserTypeProfessor, registerLoanUser);
			
			compoLoanUserTypePostgraduate.setVisible(false);
			page.insertComposite(compoLoanUserTypePostgraduate, registerLoanUser);
			
			compoLoanUserTypeLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLibrarian, registerLoanUser);
			
			compoLoanUserTypeLoanInterLibrarian.setVisible(false);
			page.insertComposite(compoLoanUserTypeLoanInterLibrarian, registerLoanUser);
			
			compoOther.setVisible(false);
			page.insertComposite(compoOther, registerLoanUser);
			
			compoLoanUserTypeStudents.getShell().getShell().layout(true, true);
			compoLoanUserTypeStudents.getShell().getShell().redraw();
			compoLoanUserTypeStudents.getShell().getShell().update();
			
			compoLoanUserTypeProfessor.getShell().getShell().layout(true, true);
			compoLoanUserTypeProfessor.getShell().getShell().redraw();
			compoLoanUserTypeProfessor.getShell().getShell().update();
			
			compoLoanUserTypePostgraduate.getShell().getShell().layout(true, true);
			compoLoanUserTypePostgraduate.getShell().getShell().redraw();
			compoLoanUserTypePostgraduate.getShell().getShell().update();
			
			compoLoanUserTypeLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLibrarian.getShell().getShell().update();
			
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().layout(true, true);
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().redraw();
			compoLoanUserTypeLoanInterLibrarian.getShell().getShell().update();
			
			compoOther.getShell().getShell().layout(true, true);
			compoOther.getShell().getShell().redraw();
			compoOther.getShell().getShell().update();
			
		}
			
}
}
