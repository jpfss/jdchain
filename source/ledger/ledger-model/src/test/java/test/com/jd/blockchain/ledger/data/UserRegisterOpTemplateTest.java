/**
 * Copyright: Copyright 2016-2020 JD.COM All Right Reserved
 * FileName: test.com.jd.blockchain.ledger.data.UserRegisterOpTemplateTest
 * Author: shaozhuguang
 * Department: 区块链研发部
 * Date: 2018/8/30 上午11:04
 * Description:
 */
package test.com.jd.blockchain.ledger.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jd.blockchain.binaryproto.BinaryEncodingUtils;
import com.jd.blockchain.binaryproto.DataContractRegistry;
import com.jd.blockchain.crypto.CryptoKeyPair;
import com.jd.blockchain.crypto.CryptoServiceProviders;
import com.jd.blockchain.crypto.PubKey;
import com.jd.blockchain.ledger.BlockchainIdentity;
import com.jd.blockchain.ledger.BlockchainIdentityData;
import com.jd.blockchain.ledger.Operation;
import com.jd.blockchain.ledger.UserRegisterOperation;
import com.jd.blockchain.ledger.data.UserRegisterOpTemplate;

/**
 *
 * @author shaozhuguang
 * @create 2018/8/30
 * @since 1.0.0
 */

public class UserRegisterOpTemplateTest {

    private UserRegisterOpTemplate data;

    @Before
    public void initUserRegisterOpTemplate() {
        DataContractRegistry.register(UserRegisterOperation.class);
        DataContractRegistry.register(Operation.class);
        CryptoKeyPair key = CryptoServiceProviders.getSignatureFunction("ED25519").generateKeyPair();
        PubKey pubKey = key.getPubKey();
        BlockchainIdentity contractID = new BlockchainIdentityData(pubKey);
        data = new UserRegisterOpTemplate(contractID);
    }

    @Test
    public void testSerialize_UserRegisterOperation() throws Exception {
        byte[] serialBytes = BinaryEncodingUtils.encode(data, UserRegisterOperation.class);
        UserRegisterOperation resolvedData = BinaryEncodingUtils.decode(serialBytes);
        System.out.println("------Assert start ------");
        assertEquals(data.getUserID().getAddress(), resolvedData.getUserID().getAddress());
        assertEquals(data.getUserID().getPubKey(), resolvedData.getUserID().getPubKey());
        System.out.println("------Assert OK ------");
    }

    @Test
    public void testSerialize_Operation() throws Exception {
        byte[] serialBytes = BinaryEncodingUtils.encode(data, Operation.class);
        Operation resolvedData = BinaryEncodingUtils.decode(serialBytes);
        System.out.println("------Assert start ------");
        System.out.println("serialBytesLength=" + serialBytes.length);
        System.out.println(resolvedData);
        System.out.println("------Assert OK ------");
    }
}