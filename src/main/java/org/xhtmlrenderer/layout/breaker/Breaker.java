//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.xhtmlrenderer.layout.breaker;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xhtmlrenderer.css.constants.IdentValue;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.layout.LineBreakContext;
import org.xhtmlrenderer.layout.TextUtil;
import org.xhtmlrenderer.render.FSFont;

public class Breaker {
    private static final String DEFAULT_LANGUAGE = System.getProperty("org.xhtmlrenderer.layout.breaker.default-language", "en");

    public Breaker() {
    }

    public static void breakFirstLetter(LayoutContext c, LineBreakContext context, int avail, CalculatedStyle style) {
        FSFont font = style.getFSFont(c);
        context.setEnd(getFirstLetterEnd(context.getMaster(), context.getStart()));
        context.setWidth(c.getTextRenderer().getWidth(c.getFontContext(), font, context.getCalculatedSubstring()));
        if (context.getWidth() > avail) {
            context.setNeedsNewLine(true);
            context.setUnbreakable(true);
        }

    }

    private static int getFirstLetterEnd(String text, int start) {
        boolean letterFound = false;
        int end = text.length();

        for(int i = start; i < end; ++i) {
            char currentChar = text.charAt(i);
            if (!TextUtil.isFirstLetterSeparatorChar(currentChar)) {
                if (letterFound) {
                    return i;
                }

                letterFound = true;
            }
        }

        return end;
    }

    public static void breakText(LayoutContext c, LineBreakContext context, int avail, CalculatedStyle style) {
        FSFont font = style.getFSFont(c);
        IdentValue whitespace = style.getWhitespace();
        if (whitespace == IdentValue.NOWRAP) {
            context.setEnd(context.getLast());
            context.setWidth(c.getTextRenderer().getWidth(c.getFontContext(), font, context.getCalculatedSubstring()));
        } else {
            if (whitespace == IdentValue.PRE || whitespace == IdentValue.PRE_WRAP || whitespace == IdentValue.PRE_LINE) {
                int n = context.getStartSubstring().indexOf("\n");
                if (n > -1) {
                    context.setEnd(context.getStart() + n + 1);
                    context.setWidth(c.getTextRenderer().getWidth(c.getFontContext(), font, context.getCalculatedSubstring()));
                    context.setNeedsNewLine(true);
                    context.setEndsOnNL(true);
                } else if (whitespace == IdentValue.PRE) {
                    context.setEnd(context.getLast());
                    context.setWidth(c.getTextRenderer().getWidth(c.getFontContext(), font, context.getCalculatedSubstring()));
                }
            }

            if (whitespace != IdentValue.PRE && (!context.isNeedsNewLine() || context.getWidth() > avail)) {
                context.setEndsOnNL(false);
                doBreakText(c, context, avail, style, false);
            }
        }
    }

    private static int getWidth(LayoutContext c, FSFont f, String text) {
        return c.getTextRenderer().getWidth(c.getFontContext(), f, text);
    }

    public static BreakPointsProvider getBreakPointsProvider(String text, LayoutContext c, Element element, CalculatedStyle style) {
        return c.getSharedContext().getLineBreakingStrategy().getBreakPointsProvider(text, getLanguage(c, element), style);
    }

    public static BreakPointsProvider getBreakPointsProvider(String text, LayoutContext c, Text textNode, CalculatedStyle style) {
        return c.getSharedContext().getLineBreakingStrategy().getBreakPointsProvider(text, getLanguage(c, textNode), style);
    }

    private static String getLanguage(LayoutContext c, Element element) {
        String language = c.getNamespaceHandler().getLang(element);
        if (language == null || language.isEmpty()) {
            language = DEFAULT_LANGUAGE;
        }

        return language;
    }

    private static String getLanguage(LayoutContext c, Text textNode) {
        if (textNode != null) {
            Node parentNode = textNode.getParentNode();
            if (parentNode instanceof Element) {
                return getLanguage(c, (Element)parentNode);
            }
        }

        return DEFAULT_LANGUAGE;
    }

    private static void doBreakText(LayoutContext c, LineBreakContext context, int avail, CalculatedStyle style, boolean tryToBreakAnywhere) {
        FSFont f = style.getFSFont(c);
        String currentString = context.getStartSubstring();
        BreakPointsProvider iterator = getBreakPointsProvider(currentString, c, context.getTextNode(), style);
        if (tryToBreakAnywhere) {
            iterator = new BreakAnywhereLineBreakStrategy(currentString);
        }

        BreakPoint bp = ((BreakPointsProvider)iterator).next();
        BreakPoint lastBreakPoint = null;
        int right = -1;
        int previousWidth = 0;

        for(int previousPosition = 0; bp != null && bp.getPosition() != -1; bp = ((BreakPointsProvider)iterator).next()) {
            int currentWidth = getWidth(c, f, currentString.substring(previousPosition, bp.getPosition()) + bp.getHyphen());
            int widthWithHyphen = previousWidth + currentWidth;
            previousWidth = widthWithHyphen;
            previousPosition = bp.getPosition();
            if (widthWithHyphen > avail) {
                break;
            }

            right = previousPosition;
            lastBreakPoint = bp;
        }

        if (bp != null && bp.getPosition() != -1 && right >= 0 && !lastBreakPoint.getHyphen().isEmpty()) {
            context.setMaster((new StringBuilder(context.getMaster())).insert(context.getStart() + right, lastBreakPoint.getHyphen()).toString());
            right += lastBreakPoint.getHyphen().length();
        }

        if (bp != null && bp.getPosition() == -1) {
            context.setWidth(getWidth(c, f, currentString));
            context.setEnd(context.getMaster().length());
        } else {
            context.setNeedsNewLine(true);
            if (right <= 0 && style.getWordWrap() == IdentValue.BREAK_WORD && !tryToBreakAnywhere) {
                doBreakText(c, context, avail, style, true);
            } else if (right > 0) {
                context.setEnd(context.getStart() + right);
                context.setWidth(getWidth(c, f, context.getMaster().substring(context.getStart(), context.getStart() + right)));
            } else {
                context.setEnd(context.getStart() + currentString.length());
                context.setUnbreakable(true);
                context.setWidth(getWidth(c, f, context.getCalculatedSubstring()));
            }
        }
    }
}
