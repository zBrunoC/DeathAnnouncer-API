package net.zbrunoc.api.model;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Typed black-box sample. Parsed from the JSON stored on {@link DeathLogEntry} without a third-party library.
 */
public record DeathTelemetryData(
        double tps,
        double mspt,
        int victimPing,
        int killerPing,
        int nearbyEntities32,
        float fallDistance,
        double velocityLength,
        boolean gliding,
        boolean inLava,
        boolean inWater,
        String activeEffects,
        double lastHitDistance,
        boolean meleePlayerHit,
        double maceFallHeight
) {

    public static DeathTelemetryData fromJson(String raw) {
        Map<String, String> values = flat(raw);
        if (values.isEmpty()) {
            return null;
        }
        return new DeathTelemetryData(
                number(values.get("tps"), -1),
                number(values.get("mspt"), -1),
                (int) number(values.get("victimPing"), -1),
                (int) number(values.get("killerPing"), -1),
                (int) number(values.get("nearbyEntities32"), -1),
                (float) number(values.get("fallDistance"), 0),
                number(values.get("velocityLength"), 0),
                bool(values.get("gliding")),
                bool(values.get("inLava")),
                bool(values.get("inWater")),
                values.getOrDefault("activeEffects", ""),
                number(values.get("lastHitDistance"), -1),
                bool(values.get("meleePlayerHit")),
                number(values.get("maceFallHeight"), -1)
        );
    }

    public String toJson() {
        return "{"
                + "\"tps\":" + tps
                + ",\"mspt\":" + mspt
                + ",\"victimPing\":" + victimPing
                + ",\"killerPing\":" + killerPing
                + ",\"nearbyEntities32\":" + nearbyEntities32
                + ",\"fallDistance\":" + fallDistance
                + ",\"velocityLength\":" + velocityLength
                + ",\"gliding\":" + gliding
                + ",\"inLava\":" + inLava
                + ",\"inWater\":" + inWater
                + ",\"activeEffects\":\"" + escape(activeEffects) + "\""
                + ",\"lastHitDistance\":" + lastHitDistance
                + ",\"meleePlayerHit\":" + meleePlayerHit
                + ",\"maceFallHeight\":" + maceFallHeight
                + "}";
    }

    private static Map<String, String> flat(String raw) {
        Map<String, String> values = new LinkedHashMap<>();
        if (raw == null) {
            return values;
        }
        int i = 0;
        int length = raw.length();
        while (i < length) {
            int keyStart = raw.indexOf('"', i);
            if (keyStart < 0) {
                break;
            }
            int keyEnd = raw.indexOf('"', keyStart + 1);
            if (keyEnd < 0) {
                break;
            }
            String key = raw.substring(keyStart + 1, keyEnd);
            int colon = raw.indexOf(':', keyEnd + 1);
            if (colon < 0) {
                break;
            }
            int valueStart = colon + 1;
            while (valueStart < length && Character.isWhitespace(raw.charAt(valueStart))) {
                valueStart++;
            }
            if (valueStart >= length) {
                break;
            }
            String value;
            if (raw.charAt(valueStart) == '"') {
                int valueEnd = valueStart + 1;
                StringBuilder text = new StringBuilder();
                while (valueEnd < length) {
                    char current = raw.charAt(valueEnd);
                    if (current == '\\' && valueEnd + 1 < length) {
                        text.append(raw.charAt(valueEnd + 1));
                        valueEnd += 2;
                        continue;
                    }
                    if (current == '"') {
                        break;
                    }
                    text.append(current);
                    valueEnd++;
                }
                value = text.toString();
                i = valueEnd + 1;
            } else {
                int valueEnd = valueStart;
                while (valueEnd < length) {
                    char current = raw.charAt(valueEnd);
                    if (current == ',' || current == '}') {
                        break;
                    }
                    valueEnd++;
                }
                value = raw.substring(valueStart, valueEnd).trim();
                i = valueEnd;
            }
            values.put(key, value);
        }
        return values;
    }

    private static double number(String raw, double fallback) {
        if (raw == null || raw.isBlank()) {
            return fallback;
        }
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private static boolean bool(String raw) {
        return "true".equalsIgnoreCase(raw);
    }

    private static String escape(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String formatMspt() {
        return mspt < 0 ? "n/d" : String.format(Locale.US, "%.1f", mspt);
    }

    public String formatTps() {
        return tps < 0 ? "n/d" : String.format(Locale.US, "%.1f", tps);
    }
}
