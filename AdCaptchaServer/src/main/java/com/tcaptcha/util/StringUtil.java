package com.tcaptcha.util;

import java.util.Vector;

/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/util/branches/sakai_2-5-4/util-util/util/src/java/org/sakaiproject/util/StringUtil.java $
 * $Id: StringUtil.java 34934 2007-09-10 22:52:23Z lance@indiana.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/



/**
 * <p>
 * StringUtil collects together some string utility classes.
 * </p>
 */
public class StringUtil
{

  /**
   * Split the source into two strings at the first occurrence of the splitter Subsequent occurrences are not treated specially, and may be part of the second string.
   * 
   * @param source
   *        The string to split
   * @param splitter
   *        The string that forms the boundary between the two strings returned.
   * @return An array of two strings split from source by splitter.
   */
  public static String[] splitFirst(String source, String splitter)
  {
    // hold the results as we find them
    Vector rv = new Vector();
    int last = 0;
    int next = 0;

    // find first splitter in source
    next = source.indexOf(splitter, last);
    if (next != -1)
    {
      // isolate from last thru before next
      rv.add(source.substring(last, next));
      last = next + splitter.length();
    }

    if (last < source.length())
    {
      rv.add(source.substring(last, source.length()));
    }

    // convert to array
    return (String[]) rv.toArray(new String[rv.size()]);
  }


}

   
    
    