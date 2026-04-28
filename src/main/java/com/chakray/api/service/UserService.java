package com.chakray.api.service;
import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.exception.CustomException;
import com.chakray.api.mapper.UserMapper;
import com.chakray.api.model.Address;
import com.chakray.api.model.User;
import com.chakray.api.util.AESUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@Data
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final UserMapper mapper = new UserMapper();
    private final String secret = "mysecretkeymysecretkeymysecretkey1234";
    private final JwtService jwtService;

    public UserService(JwtService jwtService) {
        this.jwtService = jwtService;
        initUsers();
        System.out.println("USERS: " + users.size());
    }

    private void initUsers() {
        for (int i = 1; i <= 3; i++) {
            users.add(User.builder()
                    .id(UUID.randomUUID())
                    .email("user" + i + "@mail.com")
                    .name("user" + i)
                    .phone("+15555555555")
                    .password(AESUtil.encrypt("password"))
                    .taxId("AARR99010" + i + "XXX")
                    .createdAt(nowMadagascar())
                    .addresses(List.of(
                            new Address(1, "work", "street 1", "UK"),
                            new Address(2, "home", "street 2", "AU")
                    ))
                    .build());
        }
    }

    public List<User> getUsers(String sortedBy, String filter) {
        Stream<User> stream = users.stream();

        if (filter != null && !filter.isEmpty()) {
            stream = applyFilter(stream, filter);
        }

        List<User> result = stream.toList();

        if (sortedBy != null && !sortedBy.isEmpty()) {
            result = result.stream()
                    .sorted(Comparator.comparing(u -> getFieldValue(u, sortedBy)))
                    .toList();
        }

        result.forEach(u -> u.setPassword(null));
        return result;
    }

    private Stream<User> applyFilter(Stream<User> stream, String filter) {
        System.out.println("filter: " + filter);
        System.out.println("FILTER LOGIC HIT");
        String[] parts = filter.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Invalid filter format. Expected field+op+value"
            );
        }

        String field = parts[0];
        String op = parts[1];
        String value = parts[2];

        return stream.filter(user -> {
            String fieldValue = getFieldValue(user, field);
            return switch (op) {
                case "co" -> fieldValue.contains(value);
                case "eq" -> fieldValue.equals(value);
                case "sw" -> fieldValue.startsWith(value);
                case "ew" -> fieldValue.endsWith(value);
                default -> false;
            };
        });
    }

    private String getFieldValue(User u, String field) {
        return switch (field) {
            case "email" -> u.getEmail();
            case "name" -> u.getName();
            case "phone" -> u.getPhone();
            case "tax_id" -> u.getTaxId();
            case "created_at" -> u.getCreatedAt();
            case "id" -> u.getId().toString();
            default -> "";
        };
    }

    public UserResponseDTO create(UserRequestDTO dto) {

        if (users.stream().anyMatch(u -> u.getTaxId().equals(dto.getTaxId()))) {
            throw new CustomException("tax_id already exists");
        }

        User user = mapper.toEntity(dto);

        user.setId(UUID.randomUUID());
        user.setPassword(AESUtil.encrypt(user.getPassword()));
        user.setCreatedAt(nowMadagascar());

        users.add(user);

        return mapper.toDTO(user);
    }

    public User update(UUID id, Map<String, Object> updates) {
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow();

        updates.forEach((k, v) -> {
            switch (k) {
                case "name" -> user.setName((String) v);
                case "email" -> user.setEmail((String) v);
                case "phone" -> user.setPhone((String) v);
            }
        });

        user.setPassword(null);
        return user;
    }

    public void delete(UUID id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    public String loginAndGenerateToken(String taxId, String password) {
        System.out.println("LOGIN HIT");
        // 🔍 1. Buscar usuario
        User user = users.stream()
                .filter(u -> u.getTaxId().equals(taxId))
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("User not found: " + taxId);
            return null;
        }

        // 🔐 2. Encriptar password recibido
        String encrypted = AESUtil.encrypt(password);

        // 🧪 3. Debug claro (puedes quitar después)
        System.out.println("Stored password: " + user.getPassword());
        System.out.println("Input encrypted: " + encrypted);

        // 🔒 4. Validar password
        if (!encrypted.equals(user.getPassword())) {
            System.out.println("Invalid password for user: " + taxId);
            return null;
        }

        return jwtService.generateToken(user.getTaxId());
    }

    private String nowMadagascar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return ZonedDateTime.now(ZoneId.of("Indian/Antananarivo")).format(formatter);
    }

    public boolean getTaxIdExistence(String taxId){
        return users.stream()
                .anyMatch(u -> taxId.equals(u.getTaxId()));
    }

    public String generateToken(String taxId) {
        return Jwts.builder()
                .setSubject(taxId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
