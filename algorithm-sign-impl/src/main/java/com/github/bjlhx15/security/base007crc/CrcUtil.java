package com.github.bjlhx15.security.base007crc;

public class CrcUtil {
    public static byte crc4_itu(byte[] data, int offset,int length){
        byte i;
        byte crc = 0;                // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) != 0)
                    crc = (byte) (((crc & 0xff) >> 1 ) ^ 0x0C);// 0x0C = (reverse 0x03)>>(8-4)
                else
                    crc = (byte) ((crc & 0xff) >> 1);
            }
        }
        return (byte) (crc & 0xf);
    }

    /******************************************************************************
     * Name:    CRC-5/EPC           x5+x3+1
     * Poly:    0x09
     * Init:    0x09
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc5_epc(byte data[],int offset,int length){
        byte i;
        byte crc = 0x48;        // Initial value: 0x48 = 0x09<<(8-5)
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for ( i = 0; i < 8; i++ ){
                if ( (crc & 0x80) != 0)
                    crc = (byte) ((crc << 1) ^ 0x48);        // 0x48 = 0x09<<(8-5)
                else
                    crc <<= 1;
            }
        }
        return (byte) (crc >> 3 & 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-5/ITU           x5+x4+x2+1
     * Poly:    0x15
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc5_itu(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;                // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) == 0)
                    crc = (byte) ((crc&0xff) >> 1);
                else
                    crc = (byte) (((crc&0xff) >> 1) ^ 0x15);// 0x15 = (reverse 0x15)>>(8-5)
            }
        }
        return (byte) (crc & 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-5/USB           x5+x2+1
     * Poly:    0x05
     * Init:    0x1F
     * Refin:   True
     * Refout:  True
     * Xorout:  0x1F
     * Note:
     *****************************************************************************/
    public static byte crc5_usb(byte[] data,int offset,int length){
        byte i;
        byte crc = 0x1F;                // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) == 0)
                    crc = (byte) ((crc&0xff) >> 1);
                else
                    crc = (byte) (((crc&0xff) >> 1) ^ 0x14);// 0x14 = (reverse 0x05)>>(8-5)
            }
        }
        return (byte) (crc ^ 0x1F & 0x1f);
    }

    /******************************************************************************
     * Name:    CRC-6/ITU           x6+x+1
     * Poly:    0x03
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc6_itu(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;         // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) == 0)
                    crc = (byte) ((crc&0xff) >> 1);
                else
                    crc = (byte) (((crc&0xff) >> 1) ^ 0x30);// 0x30 = (reverse 0x03)>>(8-6)
            }
        }
        return (byte) (crc & 0x3f);
    }

    /******************************************************************************
     * Name:    CRC-7/MMC           x7+x3+1
     * Poly:    0x09
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Use:     MultiMediaCard,SD,ect.
     *****************************************************************************/
    public static byte crc7_mmc(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];        // crc ^= *data; data++;
            for ( i = 0; i < 8; i++ )
            {
                if ( (crc & 0x80) ==0)
                    crc <<= 1;
                else
                    crc = (byte) ((crc << 1) ^ 0x12);        // 0x12 = 0x09<<(8-7)
            }
        }
        return (byte) (crc >> 1 & 0x7f);
    }

    /******************************************************************************
     * Name:    CRC-8               x8+x2+x+1
     * Poly:    0x07
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc8(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for ( i = 0; i < 8; i++ )
            {
                if ( (crc & 0x80) == 0)
                    crc <<= 1;
                else
                    crc = (byte) ((crc << 1) ^ 0x07);
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-8/ITU           x8+x2+x+1
     * Poly:    0x07
     * Init:    0x00
     * Refin:   False
     * Refout:  False
     * Xorout:  0x55
     * Alias:   CRC-8/ATM
     *****************************************************************************/
    public static byte crc8_itu(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for ( i = 0; i < 8; i++ )
            {
                if ( (crc & 0x80) == 0)
                    crc <<= 1;
                else
                    crc = (byte) ((crc << 1) ^ 0x07);
            }
        }
        return (byte) (crc ^ 0x55);
    }

    /******************************************************************************
     * Name:    CRC-8/ROHC          x8+x2+x+1
     * Poly:    0x07
     * Init:    0xFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Note:
     *****************************************************************************/
    public static byte crc8_rohc(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for ( i = 0; i < 8; i++ )
            {
                if ( (crc & 0x80) == 0)
                    crc = (byte) ((crc&0xff) >> 1);
                else
                    crc = (byte) (((crc&0xff) >> 1) ^ 0xE0);
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-8/MAXIM         x8+x5+x4+1
     * Poly:    0x31
     * Init:    0x00
     * Refin:   True
     * Refout:  True
     * Xorout:  0x00
     * Alias:   DOW-CRC,CRC-8/IBUTTON
     * Use:     Maxim(Dallas)'s some devices,e.g. DS18B20
     *****************************************************************************/
    public static byte crc8_maxim(byte[] data,int offset,int length){
        byte i;
        byte crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for ( i = 0; i < 8; i++ ){
                if ( (crc & 1) == 0)
                    crc = (byte) ((crc&0xff) >> 1);
                else
                    crc = (byte) (((crc&0xff) >> 1) ^ 0x8C);
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/IBM          x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Alias:   CRC-16,CRC-16/ARC,CRC-16/LHA
     *****************************************************************************/
    public static short crc16_ibm(byte data[],int offset,int length){
        byte i;
        short crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i)
            {
                if ((crc & 1) == 0)
                    crc = (short) (crc >> 1);
                else
                    crc = (short) ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/MAXIM        x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Note:
     *****************************************************************************/
    public static short crc16_maxim(byte[] data,int offset,int length){
        byte i;
        short crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) == 0)
                    crc = (short) (crc >> 1);
                else
                    crc = (short) ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return (short) ~crc;    // crc^0xffff
    }

    /******************************************************************************
     * Name:    CRC-16/USB          x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Note:
     *****************************************************************************/
    public static short crc16_usb(byte[] data,int offset,int length){
        byte i;
        short crc = (short) 0xffff;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];            // crc ^= *data; data++;
            for (i = 0; i < 8; ++i)
            {
                if ((crc & 1) == 0)
                    crc = (short) (crc >> 1);
                else
                    crc = (short) ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return (short) ~crc;    // crc^0xffff
    }

    /******************************************************************************
     * Name:    CRC-16/MODBUS       x16+x15+x2+1
     * Poly:    0x8005
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Note:
     *****************************************************************************/
    public static short crc16_modbus(byte[] data,int offset,int length){
        byte i;
        short crc = (short) 0xffff;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];            // crc ^= *data; data++;
            for (i = 0; i < 8; ++i){
                if ((crc & 1) == 0)
                    crc = (short) (crc >> 1);
                else
                    crc = (short) ((crc >> 1) ^ 0xA001);        // 0xA001 = reverse 0x8005
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/CCITT        x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0x0000
     * Alias:   CRC-CCITT,CRC-16/CCITT-TRUE,CRC-16/KERMIT
     *****************************************************************************/
    public static short crc16_ccitt(byte[] data,int offset,int length){
        byte i;
        short crc = 0;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) != 0)
                    crc = (short) ((crc >> 1) ^ 0x8408);        // 0x8408 = reverse 0x1021
                else
                    crc = (short) (crc >> 1);
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/CCITT-FALSE   x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0xFFFF
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000
     * Note:
     *****************************************************************************/
    public static short crc16_ccitt_false(byte[] data,int offset,int length){
        byte i;
        short crc = (short) 0xffff;        //Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= (short)(data[j]) << 8; // crc ^= (uint6_t)(*data)<<8; data++;
            for (i = 0; i < 8; ++i)
            {
                if ( (crc & 0x8000) != 0)
                    crc = (short) ((crc << 1) ^ 0x1021);
                else
                    crc <<= 1;
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/X25          x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0xFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0XFFFF
     * Note:
     *****************************************************************************/
    public static short crc16_x25(byte[] data,int offset,int length){
        byte i;
        short crc = (short) 0xffff;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i)
            {
                if ((crc & 1) != 0)
                    crc = (short) ((crc >> 1) ^ 0x8408);        // 0x8408 = reverse 0x1021
                else
                    crc = (short) (crc >> 1);
            }
        }
        return (short) ~crc;
    }

    /******************************************************************************
     * Name:    CRC-16/XMODEM       x16+x12+x5+1
     * Poly:    0x1021
     * Init:    0x0000
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000
     * Alias:   CRC-16/ZMODEM,CRC-16/ACORN
     *****************************************************************************/
    public static short crc16_xmodem(byte[] data, int offset,int length){
        byte i;
        short crc = 0;            // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= (short)data[j] << 8;
            for (i = 0; i < 8; ++i){
                if ( (crc & 0x8000) != 0)
                    crc = (short) ((crc << 1) ^ 0x1021);
                else
                    crc <<= 1;
            }
        }
        return crc;
    }

    /******************************************************************************
     * Name:    CRC-16/DNP          x16+x13+x12+x11+x10+x8+x6+x5+x2+1
     * Poly:    0x3D65
     * Init:    0x0000
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFF
     * Use:     M-Bus,ect.
     *****************************************************************************/
    public static short crc16_dnp(byte[] data, int offset,int length){
        byte i;
        short crc = 0;            // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) != 0)
                    crc = (short) ((crc >> 1) ^ 0xA6BC);        // 0xA6BC = reverse 0x3D65
                else
                    crc = (short) (crc >> 1);
            }
        }
        return (short) ~crc;                // crc^Xorout
    }

    /******************************************************************************
     * Name:    CRC-32  x32+x26+x23+x22+x16+x12+x11+x10+x8+x7+x5+x4+x2+x+1
     * Poly:    0x4C11DB7
     * Init:    0xFFFFFFF
     * Refin:   True
     * Refout:  True
     * Xorout:  0xFFFFFFF
     * Alias:   CRC_32/ADCCP
     * Use:     WinRAR,ect.
     *****************************************************************************/
    public static int crc32(byte[] data, int offset,int length){
        byte i;
        int crc = 0xffffffff;        // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j];
            for (i = 0; i < 8; ++i){
                if ((crc & 1) != 0)
                    crc = (crc >> 1) ^ 0xEDB88320;// 0xEDB88320= reverse 0x04C11DB7
                else
                    crc = (crc >> 1);
            }
        }
        return ~crc;
    }


    /******************************************************************************
     * Name:    CRC-32/MPEG-2  x32+x26+x23+x22+x16+x12+x11+x10+x8+x7+x5+x4+x2+x+1
     * Poly:    0x4C11DB7
     * Init:    0xFFFFFFF
     * Refin:   False
     * Refout:  False
     * Xorout:  0x0000000
     * Note:
     *****************************************************************************/
    public static int crc32_mpeg_2(byte[] data,int offset, int length){
        byte i;
        int crc = 0xffffffff;  // Initial value
        length += offset;
        for(int j=offset;j<length;j++) {
            crc ^= data[j] << 24;
            for (i = 0; i < 8; ++i){
                if ( (crc & 0x80000000) != 0)
                    crc = (crc << 1) ^ 0x04C11DB7;
                else
                    crc <<= 1;
            }
        }
        return crc;
    }

}
