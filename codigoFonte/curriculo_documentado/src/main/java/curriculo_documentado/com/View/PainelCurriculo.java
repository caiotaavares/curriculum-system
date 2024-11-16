package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.ItensDeSecao;
import curriculo_documentado.com.Model.SIstemaCurriculo;
import curriculo_documentado.com.Model.Secao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelCurriculo extends JFrame implements RefreshListener{
    private SIstemaCurriculo sistemaCurriculo;
    private JPanel mainPanel;
    private JPanel sectionsPanel;

    public PainelCurriculo() {}

    public PainelCurriculo(SIstemaCurriculo sistemaCurriculo) {
        this.sistemaCurriculo = sistemaCurriculo;

        setTitle("Currículo");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create menu bar
        setJMenuBar(createMenuBar());

        // Main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Scrollable panel for sections
        sectionsPanel = new JPanel();
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sectionsPanel);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        refreshSections(sectionsPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu secaoMenu = new JMenu("Seção");
        JMenu itensMenu = new JMenu("Itens");

        // Adicionar Seção
        JMenuItem addSecaoItem = new JMenuItem("Adicionar Seção");
        addSecaoItem.addActionListener(e -> showAddSectionDialog());

        // Alterar Seção
        JMenuItem editSecaoItem = new JMenuItem("Alterar Seção");
//        editSecaoItem.addActionListener(e -> showEditSectionDialog());

        // Excluir Seção
        JMenuItem deleteSecaoItem = new JMenuItem("Excluir Seção");
//        deleteSecaoItem.addActionListener(e -> showDeleteSectionDialog());

        // Adicionar Item
        JMenuItem addItensItem = new JMenuItem("Adicionar Item");
        addItensItem.addActionListener(e -> ShowAddItemDialog());

        itensMenu.add(addItensItem);

        secaoMenu.add(addSecaoItem);
        secaoMenu.add(editSecaoItem);
        secaoMenu.add(deleteSecaoItem);

        menuBar.add(secaoMenu);
        menuBar.add(itensMenu);
        return menuBar;
    }

    private void ShowAddItemDialog() {
        AdicionarItem dialog = new AdicionarItem(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isItemAdded()) {
            refreshSections(sectionsPanel);
        }
    }

    private void showAddSectionDialog() {
        AdicionarSecao dialog = new AdicionarSecao(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isSectionAdded()) {
            refreshSections(sectionsPanel);
        }
    }

//    private void showEditSectionDialog() {
//        EditSectionDialog dialog = new EditSectionDialog(this, sistemaCurriculo);
//        dialog.setVisible(true);
//        if (dialog.isSectionUpdated()) {
//            refreshSections();
//        }
//    }
//
//    private void showDeleteSectionDialog() {
//        DeleteSectionDialog dialog = new DeleteSectionDialog(this, sistemaCurriculo);
//        dialog.setVisible(true);
//        if (dialog.isSectionDeleted()) {
//            refreshSections();
//        }
//    }

    @Override
    public void refreshSections(JPanel sectionsPanel) {
        // Limpa o conteúdo atual do sectionsPanel
        sectionsPanel.removeAll();

        // Obtém as seções do sistema
        List<Secao> sections = sistemaCurriculo.getControlador().obterSecoes();
        Docente docente = sistemaCurriculo.getControlador().obterDocente();

        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        sectionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margem geral

        if (sections.isEmpty()) {
            addImagePanel();
        } else {
            // Adiciona título e informações do docente
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BorderLayout());
            headerPanel.add(new JLabel("<html><h1>" + docente.getNome() + "</h1></html>"), BorderLayout.NORTH);
            headerPanel.add(new JLabel("<html><h2>" + docente.getNomeInstituicao() + "</h2><hr></html>"), BorderLayout.CENTER);
            sectionsPanel.add(headerPanel);

            for (Secao section : sections) {
                // Painel para cada seção
                JPanel sectionPanel = new JPanel();
                sectionPanel.setLayout(new BorderLayout());
                sectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaçamento interno

                // Cabeçalho da seção com título e botões "Editar" e "Excluir"
                JPanel sectionHeaderPanel = new JPanel();
                sectionHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel sectionLabel = new JLabel("<html><h3>" + section.getNome() + "</h3></html>");
                JButton editSectionButton = new JButton("Editar");
                JButton deleteSectionButton = new JButton("Excluir");

                // Configura as ações dos botões de seção
                editSectionButton.addActionListener(e -> {
//                    editarSecao(section);
                    refreshSections(sectionsPanel);
                });
                deleteSectionButton.addActionListener(e -> {
//                    excluirSecao(section);
                    refreshSections(sectionsPanel);
                });

                // Adiciona os botões e o título ao painel do cabeçalho da seção
                sectionHeaderPanel.add(editSectionButton);
                sectionHeaderPanel.add(deleteSectionButton);
                sectionHeaderPanel.add(sectionLabel);
                sectionPanel.add(sectionHeaderPanel, BorderLayout.NORTH);

                // Painel para os itens da seção
                JPanel itemsPanel = new JPanel();
                itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

                // Adiciona cada item com os botões "Editar" e "Excluir" à esquerda
                for (ItensDeSecao item : section.getItensDeSecao()) {
                    JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel itemLabel = new JLabel("<html><b>" + item.getNome() + "</b>: " + item.getDescricao() + "</html>");
                    JButton editItemButton = new JButton("Editar");
                    JButton deleteItemButton = new JButton("Excluir");

                    // Configura as ações dos botões de item
                    editItemButton.addActionListener(e -> {
//                        editarItem(item);
                        refreshSections(sectionsPanel);
                    });
                    deleteItemButton.addActionListener(e -> {
//                        excluirItem(section, item);
                        refreshSections(sectionsPanel);
                    });

                    // Adiciona os botões e o rótulo ao painel do item
                    itemPanel.add(editItemButton);
                    itemPanel.add(deleteItemButton);
                    itemPanel.add(itemLabel);
                    itemsPanel.add(itemPanel);
                }

                // Adiciona botão para adicionar um novo item
                JButton addItemButton = new JButton("Adicionar Item");
                addItemButton.addActionListener(e -> {
//                    adicionarItemNaSecao(section);
                    refreshSections(sectionsPanel);
                });
                itemsPanel.add(addItemButton);

                // Adiciona itemsPanel ao sectionPanel
                sectionPanel.add(itemsPanel, BorderLayout.LINE_START);

                // Adiciona sectionPanel ao sectionsPanel
                sectionsPanel.add(sectionPanel);
            }

            // Botão para adicionar uma nova seção
            JButton addSectionButton = new JButton("Adicionar Seção");
            addSectionButton.addActionListener(e -> {
//                adicionarNovaSecao();
                refreshSections(sectionsPanel);
            });
            sectionsPanel.add(addSectionButton);
        }

        // Atualiza a interface
        sectionsPanel.revalidate();
        sectionsPanel.repaint();
    }


    private void addImagePanel() {
        // Criar o painel para exibir a imagem, ocupando todo o espaço do sectionsPanel
        JPanel imagePanel = new JPanel(new GridBagLayout()); // GridBagLayout centraliza o conteúdo
        imagePanel.setPreferredSize(sectionsPanel.getSize()); // Garante que o painel ocupe o espaço do sectionsPanel

        // Carregar a imagem da pasta src/main/resources
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/sem_resultado.png"));

        // Redimensionar a imagem para 200x200 pixels
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        // Criar um JLabel para exibir a imagem redimensionada
        JLabel imageLabel = new JLabel(resizedIcon);

        // Adicionar o JLabel ao painel de imagem
        imagePanel.add(imageLabel, new GridBagConstraints()); // Centraliza o JLabel no painel

        // Adicionar o painel de imagem ao painel principal de seções
        sectionsPanel.add(imagePanel);
    }

    private void addSectionPanel(Secao section) {
        // Cria o conteúdo HTML para a seção e seus itens
        StringBuilder htmlContent = new StringBuilder("<html>");

        // Adiciona o título da seção (nome)
        htmlContent.append("<h2>").append(section.getNome()).append("</h2>");

        // Adiciona cada item da seção em uma lista
        htmlContent.append("<ul>");
        for (ItensDeSecao item : section.getItensDeSecao()) {
            htmlContent.append("<li><b>").append(item.getNome()).append("</b>: ")
                    .append(item.getDescricao()).append("</li>");
        }
        htmlContent.append("</ul>");
        htmlContent.append("</html>");

        // Cria o JEditorPane para exibir o conteúdo HTML
        JEditorPane sectionEditorPane = new JEditorPane();
        sectionEditorPane.setContentType("text/html");
        sectionEditorPane.setText(htmlContent.toString());
        sectionEditorPane.setEditable(false); // Torna o campo de texto somente leitura

        // Adiciona um JScrollPane para rolagem, se necessário
        JScrollPane scrollPane = new JScrollPane(sectionEditorPane);
        scrollPane.setPreferredSize(new Dimension(300, 100)); // Ajuste conforme necessário

        // Adiciona o JScrollPane ao painel principal de seções
        sectionsPanel.add(scrollPane);
    }

}