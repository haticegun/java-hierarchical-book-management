package datastructureproject;

public class Utilities {

    public static void print(String message) {
        System.out.println(message);
    }

    public static int length(String text) {
        if (text == null) return 0;

        int count = 0;
        while (true) {
            try {
                text.charAt(count);
                count++;
            } catch (Exception e) {
                break;
            }
        }
        return count;
    }

    public static int arraySize(String[] array) {
        if (array == null) return 0;

        int count = 0;
        try {
            while (true) {
                array[count].toString();
                count++;
            }
        } catch (Exception e) {
        }
        return count;
    }

    public static int filledLength(int[] array) {
        if (array == null) return 0;

        int count = 0;
        while (count < 50) {
            if (array[count] == 0) break;
            count++;
        }
        return count;
    }

    public static boolean equalsText(String text1, String text2) {
        if (text1 == null || text2 == null) return false;

        int len1 = length(text1);
        int len2 = length(text2);
        if (len1 != len2) return false;

        for (int i = 0; i < len1; i++) {
            if (text1.charAt(i) != text2.charAt(i)) return false;
        }
        return true;
    }

    public static String trimText(String text) {
        if (text == null) return "";

        int textLength = length(text);
        if (textLength == 0) return "";

        int start = 0;
        int end = textLength - 1;

        while (start <= end && isWhitespace(text.charAt(start))) start++;
        while (end >= start && isWhitespace(text.charAt(end))) end--;

        if (start > end) return "";

        String trimmedText = "";
        for (int i = start; i <= end; i++) {
            trimmedText += text.charAt(i);
        }
        return trimmedText;
    }

    private static boolean isWhitespace(char character) {
        return character == ' ' || character == '\t' || character == '\n' || character == '\r';
    }

    public static String[] splitText(String text, char separator) {
        if (text == null) return new String[0];

        int textLength = length(text);
        int partCount = 1;

        for (int i = 0; i < textLength; i++) {
            if (text.charAt(i) == separator) partCount++;
        }

        String[] result = new String[partCount];
        int partIndex = 0;
        String tempText = "";

        for (int i = 0; i < textLength; i++) {
            char character = text.charAt(i);

            if (character == separator) {
                result[partIndex] = tempText;
                partIndex++;
                tempText = "";
            } else {
                tempText += character;
            }
        }

        result[partIndex] = tempText;
        return result;
    }

    public static String toLowerCaseTurkish(String text) {
        if (text == null) return "";

        int textLength = length(text);
        String result = "";

        for (int i = 0; i < textLength; i++) {
            char character = text.charAt(i);

            if (character == 'İ') character = 'i';
            else if (character == 'I') character = 'ı';
            else if (character == 'Ş') character = 'ş';
            else if (character == 'Ç') character = 'ç';
            else if (character == 'Ğ') character = 'ğ';
            else if (character == 'Ü') character = 'ü';
            else if (character == 'Ö') character = 'ö';
            else if (character >= 'A' && character <= 'Z') character = (char) (character + 32);

            result += character;
        }

        return result;
    }

    public static boolean validateHierarchy(String position) {
        position = trimText(position);
        if (equalsText(position, "")) return false;

        int positionLength = length(position);
        if (positionLength == 0) return false;
        if (position.charAt(0) == '.' || position.charAt(positionLength - 1) == '.') return false;

        boolean previousDot = false;
        boolean hasDigit = false;

        for (int i = 0; i < positionLength; i++) {
            char character = position.charAt(i);

            if (character == '.') {
                if (previousDot || !hasDigit) return false;
                previousDot = true;
                hasDigit = false;
            } else {
                if (character < '0' || character > '9') return false;
                previousDot = false;
                hasDigit = true;
            }
        }

        return hasDigit;
    }

    public static int[] parseLevels(String position) {
        int[] result = new int[50];
        if (position == null) return result;

        int positionLength = length(position);

        int levelIndex = 0;
        int currentNumber = 0;
        boolean hasNumber = false;

        for (int i = 0; i < positionLength; i++) {
            char character = position.charAt(i);

            if (character >= '0' && character <= '9') {
                hasNumber = true;
                currentNumber = currentNumber * 10 + (character - '0');
            } else if (character == '.') {
                if (hasNumber) {
                    result[levelIndex] = currentNumber;
                    levelIndex++;
                    currentNumber = 0;
                    hasNumber = false;
                    if (levelIndex >= 50) break;
                }
            }
        }

        if (hasNumber && levelIndex < 50) {
            result[levelIndex] = currentNumber;
        }

        return result;
    }

    public static String buildPrefix(int[] path, int level) {
        if (path == null) return "";
        if (level < 0) return "";

        String prefix = "";

        int i = 0;
        while (i <= level && i < 50) {
            if (i > 0) prefix += ".";
            prefix += path[i];
            i++;
        }

        return prefix;
    }

    public static String createIndent(int count) {
        if (count <= 0) return "";

        String spaces = "";
        int i = 0;
        while (i < count) {
            spaces += ' ';
            i++;
        }
        return spaces;
    }
}
