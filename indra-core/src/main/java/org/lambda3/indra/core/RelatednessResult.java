package org.lambda3.indra.core;

import org.lambda3.indra.common.client.ScoredTextPair;
import org.lambda3.indra.common.client.TextPair;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RelatednessResult {
    private Map<TextPair, ScoredTextPair> results;

    public RelatednessResult(Collection<ScoredTextPair> scoredTerms) {
        if (scoredTerms == null) {
            throw new IllegalArgumentException("scoreTerms can't be null");
        }
        results = new LinkedHashMap<>();
        for (ScoredTextPair scoredTextPair : scoredTerms) {
            results.put(new TextPair(scoredTextPair.t1, scoredTextPair.t2), scoredTextPair);
        }
    }

    public ScoredTextPair getScore(TextPair pair) {
        return results.get(pair);
    }

    public Collection<ScoredTextPair> getScores() {
        return results.values();
    }
}
