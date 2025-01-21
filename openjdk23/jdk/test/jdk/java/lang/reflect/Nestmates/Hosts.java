/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

// Non-public classes so we can keep them all in this one source file

class HostOfMemberNoHost {
    // Missing NestHost attribute
    static class MemberNoHost {}
}

class HostOfMemberMissingHost {
    // Missing NestHost class
    static class MemberMissingHost {}
}

class HostOfMemberNotInstanceHost {
    // Invalid NestHost class (not instance class)
    static class MemberNotInstanceHost {
        Object[] oa; // create CP entry to use in jcod change
    }
}

class HostOfMemberNotOurHost {
    // Valid but different NestHost class
    static class MemberNotOurHost {}
}

class HostOfMemberMalformedHost {
    // Malformed NestHost class
    static class MemberMalformedHost {}
}

// Host lists itself as a member along
// with real member.
class HostWithSelfMember {
    static class Member {}
}

// Host lists duplicate members.
class HostWithDuplicateMembers {
    static class Member1 {}
    static interface Member2 {}
}
