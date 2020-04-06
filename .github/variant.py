#!/usr/bin/env python

import sys

requested, githubRef = sys.argv[1:]
isRelease = "refs/tags/v" in githubRef
track = "production" if isRelease else "internal"
variant = "release" if isRelease else "beta"

if requested == "track":
    print(track)
elif requested == "variant":
    print(variant)
elif requested == "Variant":
    print(variant.capitalize())
else:
    raise ValueError("unexpected requested " + requested)
