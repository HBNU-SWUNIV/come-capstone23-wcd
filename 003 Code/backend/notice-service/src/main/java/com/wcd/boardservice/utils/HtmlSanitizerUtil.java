package com.wcd.boardservice.utils;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class HtmlSanitizerUtil {

    public static String sanitize(String html) {
        PolicyFactory  policy = new HtmlPolicyBuilder()
                .allowElements("a", "label", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote",
                        "p", "i", "b", "u", "strong", "em", "strike", "code", "hr", "br", "div",
                        "table", "thead", "caption", "tbody", "tr", "th", "td", "pre", "ol", "ul", "li",
                        "font", "map", "span", "img", "article", "aside", "figcaption", "figure",
                        "footer", "header", "main", "nav", "section", "summary", "time")
                .allowUrlProtocols("http", "https")
                .allowAttributes("src", "alt", "border", "cellpadding", "cellspacing", "style", "height", "width")
                .onElements("img")
                .allowAttributes("charset", "type")
                .onElements("script")
                .allowAttributes("name", "target", "href", "type")
                .onElements("a")
                .toFactory();
        return policy.sanitize(html);
    }
}