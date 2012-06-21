package com.scarytom;

import java.io.IOException;

import junit.framework.Assert;
import nu.xom.ParsingException;

import org.junit.Test;

public class ParseTest {

    @Test
    public void canParse() throws IOException, ParsingException {
        NetworkBuilder networkBuilder =
            NetworkBuilder.buildNetwork("src/main/resources/ProNet.xml");
        String expected =
            "Programmer\tSkills\tRecommends\n"
                + "Bill\tPHP, Perl, Ruby\tJason, Jill, Nick, Stu\n"
                + "Dave\tC#, Java\tJill\n"
                + "Ed\tC++, Python\tLiz, Rick\n"
                + "Frank\tC++, Perl, Ruby\tNick\n"
                + "Jason\tJava, Ruby\tDave, Liz\n"
                + "Jill\tC++, Ruby\tNick\n" + "Liz\tC#, C++, Java\tBill\n"
                + "Nick\tC#, Java\tStu\n" + "Rick\tJava, PHP\tEd\n"
                + "Stu\tC++, Perl\tFrank";
        Assert.assertEquals(expected, networkBuilder.build().printout());
    }

}
