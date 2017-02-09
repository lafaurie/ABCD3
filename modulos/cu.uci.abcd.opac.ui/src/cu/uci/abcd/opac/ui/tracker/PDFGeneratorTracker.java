package cu.uci.abcd.opac.ui.tracker;

import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cu.uci.abos.api.util.ServiceListener;
import cu.uci.abos.reports.PDFReportGenerator;

public class PDFGeneratorTracker extends ServiceTracker<PDFReportGenerator, PDFReportGenerator> {
	ServiceListener<Object> PDFGeneratorServiceListener;
	PDFReportGenerator service;

	public PDFGeneratorTracker() {
		super(FrameworkUtil.getBundle(PDFGeneratorTracker.class).getBundleContext(), PDFReportGenerator.class, null);
	}

	@Override
	public PDFReportGenerator addingService(ServiceReference<PDFReportGenerator> reference) {
		service = super.addingService(reference);
		if (PDFGeneratorServiceListener != null) {
			PDFGeneratorServiceListener.addServiceListener(service);
		}
		return service;
	}

	public void setPDFGeneratorServiceListener(ServiceListener<Object> PDFGeneratorServiceListener) {
		this.PDFGeneratorServiceListener = PDFGeneratorServiceListener;
		if (service != null) {
			PDFGeneratorServiceListener.addServiceListener(service);
		}
	}
}