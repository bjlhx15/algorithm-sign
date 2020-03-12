package com.github.bjlhx15.security.encryptSign001BcEcc;

import java.util.Map;

public class BcEccAlgorithmUtil extends AbstractBcEccAlgorithmUtil{

    public static Map.Entry<String, String> initKeyPairBase64() throws Exception {
        return initKeyPairBase64("EC");
    }
}
