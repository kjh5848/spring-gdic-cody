package co.kr.nomadlab.springgdic.consts;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface AdminConst {

    User admin = new User(1L, "admin", "1234", "admin@nate.com", RoleType.ADMIN, UserStatus.ACTIVE);

    User user = new User(1L, "kkr", "1234", "kkr@nate.com", RoleType.USER, UserStatus.ACTIVE);

    Board board = new Board(1L, "첫 번째 게시물 입니다.", "첫 번째 게시물 내용", 0, 0, BoardStatus.ACTIVE,  UserConst.user);

    BoardFile boardFile = new BoardFile(1L, "fileName", "fileContent", "fileExtension", 100L, board);

    Supply supply = new Supply(1L, "첫 번째 공급공고", "첫 번째 공급공고 내용", 0, 0, SupplyStatus.ACTIVE, UserConst.user);

    SupplyFile supplyFile = new SupplyFile(1L, "fileName", "fileContent", "fileExtension", 100L, supply);

    Customer customer = new Customer(1L, "enterPriseName", "managerName", "010-1234-1234", "customerAddress", "문의내용");

    PopupFile popupFile = new PopupFile(1L, "fileName", "fileContent", "fileExtension", 100L, "Y", "http://gdic.co.kr/web/");

    PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Order.desc("id"), Sort.Order.asc("enterpriseName")));
}
