/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.morecosmetics.utils.KeyMappings
 */
package com.cosmeticsmod.morecosmetics.utils;

import java.util.HashMap;

/*
 * Exception performing whole class analysis ignored.
 */
public enum KeyMappings {
    LEFT_MOUSE(-100, 0),
    RIGHT_MOUSE(-99, 1),
    MIDDLE_MOUSE(-98, 2),
    MOUSE_4(-97, 3),
    MOUSE_5(-96, 4),
    MOUSE_6(-95, 5),
    MOUSE_7(-94, 6),
    MOUSE_8(-93, 7),
    KEY_NONE(0, -1),
    KEY_ESCAPE(1, 256),
    KEY_1(2, 49),
    KEY_2(3, 50),
    KEY_3(4, 51),
    KEY_4(5, 52),
    KEY_5(6, 53),
    KEY_6(7, 54),
    KEY_7(8, 55),
    KEY_8(9, 56),
    KEY_9(10, 57),
    KEY_0(11, 48),
    KEY_MINUS(12, 45),
    KEY_EQUALS(13, 61),
    KEY_BACK(14, 259),
    KEY_TAB(15, 258),
    KEY_Q(16, 81),
    KEY_W(17, 87),
    KEY_E(18, 69),
    KEY_R(19, 82),
    KEY_T(20, 84),
    KEY_Y(21, 89),
    KEY_U(22, 85),
    KEY_I(23, 73),
    KEY_O(24, 79),
    KEY_P(25, 80),
    KEY_LBRACKET(26, 91),
    KEY_RBRACKET(27, 93),
    KEY_RETURN(28, 257),
    KEY_LCONTROL(29, 341),
    KEY_A(30, 65),
    KEY_S(31, 83),
    KEY_D(32, 68),
    KEY_F(33, 70),
    KEY_G(34, 71),
    KEY_H(35, 72),
    KEY_J(36, 74),
    KEY_K(37, 75),
    KEY_L(38, 76),
    KEY_SEMICOLON(39, 59),
    KEY_APOSTROPHE(40, 39),
    KEY_GRAVE(41, 96),
    KEY_LSHIFT(42, 340),
    KEY_BACKSLASH(43, 92),
    KEY_Z(44, 90),
    KEY_X(45, 88),
    KEY_C(46, 67),
    KEY_V(47, 86),
    KEY_B(48, 66),
    KEY_N(49, 78),
    KEY_M(50, 77),
    KEY_COMMA(51, 44),
    KEY_PERIOD(52, 46),
    KEY_SLASH(53, 47),
    KEY_RSHIFT(54, 344),
    KEY_MULTIPLY(55, 332),
    KEY_LMENU(56, 342),
    KEY_SPACE(57, 32),
    KEY_CAPITAL(58, -2),
    KEY_F1(59, 290),
    KEY_F2(60, 291),
    KEY_F3(61, 292),
    KEY_F4(62, 293),
    KEY_F5(63, 294),
    KEY_F6(64, 295),
    KEY_F7(65, 296),
    KEY_F8(66, 297),
    KEY_F9(67, 298),
    KEY_F10(68, 299),
    KEY_NUMLOCK(69, 282),
    KEY_SCROLL(70, 281),
    KEY_NUMPAD7(71, 327),
    KEY_NUMPAD8(72, 328),
    KEY_NUMPAD9(73, 329),
    KEY_SUBTRACT(74, 333),
    KEY_NUMPAD4(75, 324),
    KEY_NUMPAD5(76, 325),
    KEY_NUMPAD6(77, 326),
    KEY_ADD(78, 334),
    KEY_NUMPAD1(79, 321),
    KEY_NUMPAD2(80, 322),
    KEY_NUMPAD3(81, 323),
    KEY_NUMPAD0(82, 320),
    KEY_DECIMAL(83, 330),
    KEY_F11(87, 300),
    KEY_F12(88, 301),
    KEY_F13(100, 302),
    KEY_F14(101, 303),
    KEY_F15(102, 304),
    KEY_F16(103, 305),
    KEY_F17(104, 306),
    KEY_F18(105, 307),
    KEY_KANA(112, 299),
    KEY_F19(113, 308),
    KEY_CONVERT(121, -2),
    KEY_NOCONVERT(123, -2),
    KEY_YEN(125, -2),
    KEY_NUMPADEQUALS(141, 336),
    KEY_CIRCUMFLEX(144, -2),
    KEY_AT(145, -2),
    KEY_COLON(146, -2),
    KEY_UNDERLINE(147, -2),
    KEY_KANJI(148, -2),
    KEY_STOP(149, -2),
    KEY_AX(150, -2),
    KEY_UNLABELED(151, -2),
    KEY_NUMPADENTER(156, 335),
    KEY_RCONTROL(157, 345),
    KEY_SECTION(167, -2),
    KEY_NUMPADCOMMA(179, -2),
    KEY_DIVIDE(181, -2),
    KEY_SYSRQ(183, -2),
    KEY_RMENU(184, 346),
    KEY_FUNCTION(196, -2),
    KEY_PAUSE(197, 284),
    KEY_HOME(199, -2),
    KEY_UP(200, 265),
    KEY_PRIOR(201, -2),
    KEY_LEFT(203, 263),
    KEY_RIGHT(205, 262),
    KEY_END(207, 269),
    KEY_DOWN(208, 264),
    KEY_NEXT(209, -2),
    KEY_INSERT(210, 260),
    KEY_DELETE(211, 261),
    KEY_CLEAR(218, -2),
    KEY_LMETA(219, -2),
    KEY_LWIN(219, 343),
    KEY_RMETA(220, -2),
    KEY_RWIN(220, 347),
    KEY_APPS(221, -2),
    KEY_POWER(222, -2),
    KEY_SLEEP(223, -2),
    KEY_LESS_THAN_MAC(167, 161),
    KEY_LESS_THAN(316, 162);

    private static final HashMap<Integer, Integer> NEW;
    private static final HashMap<Integer, Integer> OLD;
    private static boolean oldKeys;
    private final int oldKey;
    private final int newKey;

    private KeyMappings(int oldKey, int newKey) {
        this.oldKey = oldKey;
        this.newKey = newKey;
    }

    public static int getNewKey(int oldKey) {
        return NEW.getOrDefault(oldKey, -1);
    }

    public static int getOldKey(int newKey) {
        return OLD.getOrDefault(newKey, -1);
    }

    public int getKey(boolean old) {
        return old ? this.oldKey : this.newKey;
    }

    public int getKey() {
        return this.getKey(oldKeys);
    }

    public static void setOldKeys(boolean old) {
        oldKeys = old;
    }

    static {
        NEW = new HashMap();
        OLD = new HashMap();
        for (KeyMappings value : KeyMappings.values()) {
            NEW.put(value.oldKey, value.newKey);
            OLD.put(value.newKey, value.oldKey);
        }
    }
}

