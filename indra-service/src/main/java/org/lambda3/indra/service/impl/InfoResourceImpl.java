package org.lambda3.indra.service.impl;

/*-
 * ==========================License-Start=============================
 * Indra Web Service Module
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

import org.lambda3.indra.client.InfoResource;
import org.lambda3.indra.client.MetadataResponse;
import org.lambda3.indra.client.RelatednessPairRequest;
import org.lambda3.indra.client.ResourceResponse;
import org.lambda3.indra.core.VectorSpace;
import org.lambda3.indra.core.VectorSpaceFactory;
import org.lambda3.indra.core.exception.ModelNotFoundException;
import org.lambda3.indra.core.translation.TranslatorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public final class InfoResourceImpl extends InfoResource {

    private VectorSpaceFactory spaceFactory;
    private TranslatorFactory translatorFactory;

    public InfoResourceImpl(VectorSpaceFactory spaceFactory, TranslatorFactory translatorFactory) {
        this.spaceFactory = Objects.requireNonNull(spaceFactory);
        this.translatorFactory = Objects.requireNonNull(translatorFactory);
    }

    @Override
    public ResourceResponse getResources() {
        Collection<String> translationModels = translatorFactory.getAvailableModels();
        Collection<String> filteredTranslationModels = new ArrayList<>(translationModels);

        Collection<String> spaceModels = spaceFactory.getAvailableModels();
        Collection<String> filteredSpaceModels = spaceModels.stream()
                .filter(e -> !translationModels.contains(e))
                .filter(e -> !e.equals("local"))
                .collect(Collectors.toList());

        return new ResourceResponse(filteredSpaceModels, filteredTranslationModels);
    }

    @Override
    public MetadataResponse getMetadata(String model) {
        String parts[] = model.split("-");
        if (parts.length >= 3) {
            RelatednessPairRequest request = new RelatednessPairRequest().model(parts[0]).
                    language(parts[1]).corpus(String.join("-",
                    Arrays.copyOfRange(parts, 2, parts.length)));

            VectorSpace vm = spaceFactory.create(request);
            return new MetadataResponse(vm.getMetadata().asMap());
        }

        throw new ModelNotFoundException(model);
    }

}
