package com.ramadan.twitterclient.domain;

import rx.Observable;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public interface UseCase<T> {
    Observable<T> execute();
}
