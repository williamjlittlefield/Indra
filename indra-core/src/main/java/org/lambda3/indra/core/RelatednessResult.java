package org.lambda3.indra.core;

/*-
 * ==========================License-Start=============================
 * Indra Core Module
 * --------------------------------------------------------------------
 * Copyright (C) 2016 - 2017 Lambda^3
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==========================License-End===============================
 */

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
