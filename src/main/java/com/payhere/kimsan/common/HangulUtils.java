package com.payhere.kimsan.common;

public class HangulUtils {
    private static final String[] JAUM_SYLLABEL = new String[] { "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
    private static final String[] JAUM_COMPATIVILITY = new String[] { "ㄱ", "ㄲ", "", "ㄴ", "", "", "ㄷ", "ㄸ", "ㄹ", "", "", "", "", "", "", "", "ㅁ", "ㅂ", "ㅃ", "", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };

    /**
     * Finding initial jaum
     *
     * @param in CodePoint for finding jaum
     * @return initial jaum if code point is hangul, uppercase character if code point is english, # if other code point
     */
    public static final String getJaum(int intCodePoint) {
        // 조합 한글
        int c = 44032; // 0xAC00 가
        for (short i = 0; i < 19; i++) {
            if (c <= intCodePoint && intCodePoint < (c + 588)) {
                return JAUM_SYLLABEL[i];
            }
            c += 588;
        }

        // 한글 자모 (맥에서 쓰는 방식)
        c = 4352;
        for (short i = 0; i < 19; i++) {
            if (c == intCodePoint) {
                return JAUM_SYLLABEL[i];
            }
            c += 1;
        }

        // 한글 자모
        c = 12593;
        for (short i = 0; i < 30; i++) {
            if (c == intCodePoint) {
                return JAUM_COMPATIVILITY[i];
            }
            c += 1;
        }

        // 영어
        c = 65;
        for (short i = 0; i < 26; i++) {
            if (c == intCodePoint || (c + 32) == intCodePoint) {
                return Character.toString(c);
            }
            c++;
        }

        // 숫자
        c = 48;
        for (short i = 0; i < 10; i++) {
            if (c == intCodePoint) {
                return Character.toString(intCodePoint);
            }
            c++;
        }

        // 그 외
        return "#";
    }

    /**
     * Finding first jaum
     *
     * @param in text for finding first jaum
     * @return
     */
    public static final String getFirstJaum(String in) {
        return getJaum(in.codePointAt(0));
    }

    /**
     * Finding all jaum
     *
     * @param in text for finding all jaum
     * @return
     */
    public static final String getAllJaum(String in) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = in.length(); i < l; i++) {
            sb.append(getJaum(in.codePointAt(i)));
        }
        return sb.toString();
    }

    /**
     * Converting NFD String to NFC String
     *
     * This function find NFD String in input string and convert it to NFC string.
     *
     * NFD (Normalization Form Canonical Decomposition) is used in MacOS
     * NFC (Normalization Form Canonical Composition) is used in Windows and Linux
     *
     * @param in
     * @return
     */
    public static Object convertNFDToNFC(String in) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = in.length(); i < l; i++) {
            int codePoint = in.codePointAt(i);
            // 초성
            if (4352 <= codePoint && codePoint <= 4370) {
                int nfcCodePoint = 44032 + (codePoint - 4352) * 588;
                codePoint = in.codePointAt(++i);
                // 중성
                if (4449 <= codePoint && codePoint <= 4469) {
                    nfcCodePoint += (codePoint - 4449) * 28;
                    codePoint = in.codePointAt(++i);
                    // 종성
                    if (4520 <= codePoint && codePoint <= 4546) {
                        nfcCodePoint += (codePoint - 4519);
                    }
                }
                // 초성만 있는 경우
                else {
                    nfcCodePoint = codePoint - 4352 + 12593;
                }
                sb.append(Character.toString(nfcCodePoint));
            } else {
                sb.append(Character.toString(codePoint));
            }
        }
        return sb.toString();
    }

    /**
     * if input code point is Hangul, return true
     *
     * @param codePoint
     * @return
     */
    public static final boolean isHangulCodePoint(int codePoint) {
        return (44032 <= codePoint && codePoint <= 55203) || (12593 <= codePoint && codePoint <= 12643) || (4352 <= codePoint && codePoint <= 4607);
    }

    /**
     * if Hangul is found in input string, return true.
     *
     * @param in
     * @return
     */
    public static final boolean isContainsHangul(String in) {
        for (int i = 0, l = in.length(); i < l; i++) {
            if (isHangulCodePoint(in.codePointAt(i))) {
                return true;
            }
        }
        return false;
    }

}
