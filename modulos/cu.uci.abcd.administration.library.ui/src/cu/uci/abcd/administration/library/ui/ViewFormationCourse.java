package cu.uci.abcd.administration.library.ui;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cu.uci.abcd.administration.library.communFragment.ViewFormationCourseFragment;
import cu.uci.abcd.administration.library.l10n.AbosMessages;
import cu.uci.abcd.administration.library.util.Auxiliary;
import cu.uci.abcd.domain.management.library.FormationCourse;
import cu.uci.abos.api.ui.Contributor;
import cu.uci.abos.api.ui.ContributorService;
import cu.uci.abos.core.ui.ContributorPage;
import cu.uci.abos.core.util.MessageUtil;
import cu.uci.abos.core.util.RetroalimentationUtils;

/**
 * @author Yaksel Duran Rivas
 * @version 1.0.0 14/07/2016
 */

public class ViewFormationCourse extends ContributorPage implements Contributor{

	private FormationCourse formationCourse;
	private ViewFormationCourseFragment viewFormationCourseFragment;
	private Composite parentComposite;
	//private RegisterFormationCourse registerFormationCourse;
	private ContributorService contributorService;
	
	@Override
	public void setParameters(Object... parameters) {
		formationCourse = ((FormationCourse)parameters[0]);
		//registerFormationCourse = ((RegisterFormationCourse)parameters[1]);
		contributorService = ((ContributorService)parameters[2]);

	}
	
	@Override
	public Control createUIControl(Composite parent) {
		parent.setData(RWT.CUSTOM_VARIANT, "gray_background");
		viewFormationCourseFragment = new ViewFormationCourseFragment(formationCourse, true, this, contributorService);
		parentComposite = (Composite) viewFormationCourseFragment.createUIControl(parent);
		viewFormationCourseFragment.l10n();
		RetroalimentationUtils.showInformationMessage(viewFormationCourseFragment.getMsg(), MessageUtil
				.unescape(cu.uci.abos.core.l10n.AbosMessages.get().MSG_INF_CREATE_NEW_ELEMENT));
		
		return parentComposite;
	}
	
	@Override
	public String getID() {
		return "viewFormationCourse";
	}
	
	@Override
	public void l10n() {
		viewFormationCourseFragment.l10n();
		Auxiliary.refresh(parentComposite);
	}
	
	@Override
	public String contributorName() {
		return MessageUtil.unescape(AbosMessages.get().REGISTER_FORMATION_COURSE);
		//return AbosMessages.get().REGISTER_FORMATION_COURSE;
	}


}
