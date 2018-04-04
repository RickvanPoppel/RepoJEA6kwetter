package DTO;

import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;

import java.util.List;

public class DTOConvert {

    private DTOConvert() {
    }

    public static void generateUserDTOList(List<User> userList, List<UserDTO> userDTOList) {
        if(userList != null){
            userList.forEach(k -> {
                UserDTO udto = new UserDTO();
                udto.generateDTO(k);
                userDTOList.add(udto);
            });
        }
    }

    public static void generateKweetDTOList(List<Kweet> kweetList, List<KweetDTO> kweetDTOList) {
        if(kweetList != null){
            kweetList.forEach(k -> {
                KweetDTO kdto = new KweetDTO();
                kdto.generateDTO(k);
                kweetDTOList.add(kdto);
            });
        }
    }
    public static void generateHashtagDTOList(List<Hashtag> hashtagList, List<HashtagDTO> hashtagDTOList) {
        if(hashtagList != null){
            hashtagList.forEach(k -> {
                HashtagDTO hdto = new HashtagDTO();
                hdto.generateDTO(k);
                hashtagDTOList.add(hdto);
            });
        }
    }
}
