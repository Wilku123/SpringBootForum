package com.netstore.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-09-05.
 */
public class LimitedListGenerator<T> {
    public List<T> limitedList(List<T> list, int howMany)
    {
        List<T> tList = new ArrayList<T>();

        //OutOfBound
        if (list.size() < howMany) {
            tList=list.subList(0, list.size());
            return tList;
        }
        //show From Limit To Max
//        else if (list.size() < limit + howMany) {
//            return new ResponseEntity<>(list.subList(limit, list.size()), HttpStatus.OK);
//        }
        //limit less than 0 fk that guy
        else if (howMany < 0) {
            tList=list.subList(0, 0);
            return tList;
        }
        //Everything is git gut
        else {
            tList=list.subList(0, howMany);
            return tList;
        }
    }
}
