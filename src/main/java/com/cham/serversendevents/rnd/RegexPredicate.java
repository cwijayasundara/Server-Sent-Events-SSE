package com.cham.serversendevents.rnd;

import com.hazelcast.query.Predicate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPredicate implements Predicate<String, String> {

    private String regex;
    private transient Pattern pattern;

    public RegexPredicate(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean apply(Map.Entry<String, String> mapEntry) {
        if (pattern == null) {
            pattern = Pattern.compile(regex);
        }
        Matcher matcher = pattern.matcher(mapEntry.getKey());
        return matcher.matches();
    }
}