package net.ryanhecht.util;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class getUUID {
	public static UUID get(String user) {
		UUIDFetcher u = new UUIDFetcher(Arrays.asList(user));
		Map<String, UUID> response = null;
		try {
		response = u.call();
		} catch (Exception e) {
		return null;
		}
		return response.get(user);
	}
}
