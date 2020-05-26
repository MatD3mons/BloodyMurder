package fr.MatD3mons.BloodyMurder.persitence.mapper;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.persitence.Dto.BloodyPlayerDto;
import net.bloodybattle.bloodykvs.api.DomainToBloodyDto;

public class BloodyPlayerDomainToDto extends DomainToBloodyDto<BloodyPlayer, BloodyPlayerDto> {

    @Override
    public BloodyPlayerDto map(BloodyPlayer bloodyPlayer) {
        BloodyPlayerDto dto = new BloodyPlayerDto();
        dto.setArgent(bloodyPlayer.getArgent());
        dto.setGrade(bloodyPlayer.getGrade());
        dto.setLose(bloodyPlayer.getLose());
        dto.setTotaltekill(bloodyPlayer.getTotaltekill());
        dto.setWin(bloodyPlayer.getWin());
        return dto;
    }
}
