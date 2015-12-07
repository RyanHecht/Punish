package net.ryanhecht.util;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class getName {
	public static String get(UUID uuid) {
		NameFetcher u = new NameFetcher(Arrays.asList(uuid));
		Map<UUID, String> response = null;
		try {
		response = u.call();
		} catch (Exception e) {
			return null;
		}
		return response.get(uuid);
	}
}
