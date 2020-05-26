package fr.MatD3mons.BloodyMurder.persitence.mapper;

import fr.MatD3mons.BloodyMurder.GameComponents.BloodyPlayer;
import fr.MatD3mons.BloodyMurder.persitence.Dto.BloodyPlayerDto;
import net.bloodybattle.bloodykvs.api.BloodyDtoToDomain;

public class BloodyPlayerDtoToDomain extends BloodyDtoToDomain<BloodyPlayerDto, BloodyPlayer> {

    @Override
    public BloodyPlayer map(BloodyPlayerDto bloodyPlayerDto) {
        BloodyPlayer bp = new BloodyPlayer();
        bp.setStatut(bloodyPlayerDto.getTotaltekill(),bloodyPlayerDto.getArgent(),bloodyPlayerDto.getWin(),bloodyPlayerDto.getLose(),bloodyPlayerDto.getGrade());
        return bp;
    }
}
