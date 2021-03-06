// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ads.sdk.common.dynamic;

import androidx.annotation.Keep;

import com.ads.sdk.common.Logger;

import java.lang.reflect.Field;

public class ObjectWrapper<T> extends IObjectWrapper.Stub {
    @Keep public final T wrappedValue;

    private ObjectWrapper(T value) {
        this.wrappedValue = value;
    }

    public static <T> IObjectWrapper wrap(T value) {
        return new ObjectWrapper<>(value);
    }

    public static <T> T unwrap(IObjectWrapper wrapper) {
        try {
            Field valueField = wrapper.asBinder().getClass().getDeclaredField("wrappedValue");
            return (T) valueField.get(wrapper.asBinder());
        } catch (Exception e) {
            Logger.e("Cannot unwrap object...", e);
            return null;
        }
    }
}
