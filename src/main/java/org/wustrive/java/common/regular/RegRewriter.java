package org.wustrive.java.common.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:正则分组替换
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/3/30 13:39
 */
public abstract class RegRewriter {
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Constructs a rewriter using the given regular expression; the syntax is
     * the same as for 'Pattern.compile'.
     */
    public RegRewriter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    /**
     * Returns the input subsequence captured by the given group during the
     * previous match operation.
     */
    public String group(int i) {
        return matcher.group(i);
    }

    /**
     * Overridden to compute a replacement for each match. Use the method
     * 'group' to access the captured groups.
     */
    public abstract String replacement();

    /**
     * Returns the result of rewriting 'original' by invoking the method
     * 'replacement' for each match of the regular expression supplied to the
     * constructor.
     */
    public String rewrite(CharSequence original) {
        this.matcher = pattern.matcher(original);
        StringBuffer result = new StringBuffer(original.length());
        while (matcher.find()) {
            matcher.appendReplacement(result, "");
            result.append(replacement());
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
