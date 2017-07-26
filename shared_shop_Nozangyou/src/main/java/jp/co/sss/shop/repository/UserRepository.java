package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.User;

/**
 * usersテーブル用リポジトリ
 *
 * @author System Shared
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmailAndDeleteFlag(String email, int deleteFlag);

	List<User> findByDeleteFlagOrderByInsertDateDesc(int deleteFlag);
}
