package cu.uci.abcd.opac.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.client.WebClient;

import cu.uci.abos.ui.api.IEntryPointService;

public class EntryPointServiceImpl implements IEntryPointService {

	@Override
	public String getPath() {
		return "/opac";
	}

	@Override
	public Class<? extends EntryPoint> getEntryPointClass() {
		return OpacEntryPoint.class;
	}

	@Override
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "ABCD OPAC");
		return properties;
	}

}
